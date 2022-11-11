package me.siyovushbek.channels.channel;

import me.siyovushbek.channels.exception.ChannelNotFoundException;
import me.siyovushbek.channels.exception.ChannelTitleAlreadyExistsException;
import me.siyovushbek.channels.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChannelService {
    private final ChannelRepository repository;

    public ChannelService(ChannelRepository repository) {
        this.repository = repository;
    }

    public Channel findChannelById(String title) {
        return repository.findById(title)
                .orElseThrow(ChannelNotFoundException::new);
    }

    public Channel addChannel(ChannelRequest channelRequest, User admin) {
        if(repository.existsById(channelRequest.getTitle())) {
            throw new ChannelTitleAlreadyExistsException();
        }
        Channel newChannel = new Channel(channelRequest.getTitle(), admin);
        return repository.save(newChannel);
    }

    public Page<Channel> getChannelsList(int page) {
        PageRequest request = PageRequest.of(page, 10);
        return repository.findAll(request);
    }

    public boolean addMember(String title, User user) {
        Channel channel = findChannelById(title);
        Optional<User> member = channel.getMembers().stream()
                .filter((u -> u.getUsername().equals(user.getUsername())))
                .findFirst();
        if(member.isEmpty()) {
            channel.getMembers().add(user);
            repository.save(channel);
            return true;
        }
        return false;
    }
}

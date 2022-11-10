package me.siyovushbek.channels.channel;

import me.siyovushbek.channels.exception.ChannelNotFoundException;
import me.siyovushbek.channels.exception.ChannelTitleAlreadyExistsException;
import me.siyovushbek.channels.message.Message;
import me.siyovushbek.channels.message.MessageRepository;
import me.siyovushbek.channels.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ChannelService {
    private final ChannelRepository repository;
    private final MessageRepository messageRepository;

    public ChannelService(ChannelRepository repository, MessageRepository messageRepository) {
        this.repository = repository;
        this.messageRepository = messageRepository;
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

    public void addMessage(String title, Message message, User user) {
        Channel channel = findChannelById(title);

        message.setSender(user);
        message.setSentAt(LocalDateTime.now());
        message.setChannel(channel);

        messageRepository.save(message);

    }

    public void addMember(String title, User user) {
        Channel channel = findChannelById(title);
        Optional<User> member = channel.getMembers().stream()
                .filter((u -> u.getUsername().equals(user.getUsername())))
                .findFirst();
        if(member.isEmpty()) {
            channel.getMembers().add(user);
            repository.save(channel);
        }
    }
}

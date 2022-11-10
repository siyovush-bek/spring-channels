package me.siyovushbek.channels.message;

import me.siyovushbek.channels.channel.Channel;
import me.siyovushbek.channels.channel.ChannelRepository;
import me.siyovushbek.channels.exception.ChannelNotFoundException;
import me.siyovushbek.channels.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;

    private final int MESSAGES_COUNT_PER_PAGE = 20;

    public MessageService(ChannelRepository channelRepository, MessageRepository messageRepository) {
        this.channelRepository = channelRepository;
        this.messageRepository = messageRepository;
    }

    public void addMessage(String title, Message message, User user)  {
        Channel channel = channelRepository.findById(title)
                .orElseThrow(ChannelNotFoundException::new);
        message.setChannel(channel);
        message.setSender(user);
        message.setSentAt(LocalDateTime.now());

        messageRepository.save(message);
    }

    public Page<Message> getAllMessages(String title, int page) {
        Channel channel = channelRepository.findById(title)
                .orElseThrow(ChannelNotFoundException::new);
        PageRequest request = PageRequest.of(page,
                MESSAGES_COUNT_PER_PAGE,
                Sort.by(Sort.Direction.DESC, "sentAt"));
        return messageRepository.findMessageByChannel(request, channel);
    }
}

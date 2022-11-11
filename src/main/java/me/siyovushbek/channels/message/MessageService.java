package me.siyovushbek.channels.message;

import me.siyovushbek.channels.channel.Channel;
import me.siyovushbek.channels.channel.ChannelService;
import me.siyovushbek.channels.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    private final ChannelService channelService;

    private final int MESSAGES_COUNT_PER_PAGE = 20;

    @Autowired
    public MessageService(MessageRepository messageRepository, ChannelService channelService) {
        this.messageRepository = messageRepository;
        this.channelService = channelService;
    }

    public void addMessage(String title, Message message, User user)  {
        Channel channel = channelService.findChannelById(title);
        message.setChannel(channel);
        message.setSender(user);
        message.setSentAt(LocalDateTime.now());

        messageRepository.save(message);
    }

    public Page<Message> getAllMessages(String title, int page) {
        Channel channel = channelService.findChannelById(title);
        PageRequest request = PageRequest.of(page,
                MESSAGES_COUNT_PER_PAGE,
                Sort.by(Sort.Direction.DESC, "sentAt"));
        return messageRepository.findMessageByChannel(request, channel);
    }
}

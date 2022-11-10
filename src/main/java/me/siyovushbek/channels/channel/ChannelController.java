package me.siyovushbek.channels.channel;

import me.siyovushbek.channels.message.Message;
import me.siyovushbek.channels.user.User;
import me.siyovushbek.channels.user.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("api/channels")
public class ChannelController {

    private final ChannelService channelService;

    @Autowired
    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    @GetMapping
    public Page<Channel> getChannelsList(@RequestParam(defaultValue = "0") int page) {
        return channelService.getChannelsList(page);
    }

    @PostMapping
    public Channel createChannel(@RequestBody @Validated ChannelRequest channelRequest,
                                 @AuthenticationPrincipal UserDetailsImpl user) {
        return channelService.addChannel(channelRequest, user.getUser());
    }

    @GetMapping("{title}")
    public Channel getChannel(@PathVariable String title) {
        return channelService.findChannelById(title);
    }

    @PostMapping("{title}")
    public ResponseEntity<Object> sendMessageToChannel(@PathVariable String title,
                                                       @RequestBody @Validated Message message,
                                                       @AuthenticationPrincipal UserDetailsImpl user) {
        channelService.addMessage(title, message, user.getUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("{title}/members")
    public ResponseEntity<Object> joinChannel(@PathVariable String title,
                                      @AuthenticationPrincipal UserDetailsImpl user) {
        channelService.addMember(title, user.getUser());
        return ResponseEntity.ok().build();
    }

    @GetMapping("{title}/members")
    public Set<User> channelMembersList(@PathVariable String title) {
        return channelService.findChannelById(title).getMembers();
    }

//    @GetMapping("{title}/messages")
//    public List<Message> channelMessageList(@PathVariable String title) {
//        return channelService.findChannelById(title).getMessages();
//    }

}

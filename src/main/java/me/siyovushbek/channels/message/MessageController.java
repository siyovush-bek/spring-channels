package me.siyovushbek.channels.message;

import me.siyovushbek.channels.user.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {

    private final MessageService service;


    public MessageController(MessageService service) {
        this.service = service;
    }

    @GetMapping("/api/{title}/messages")
    public Page<Message> getMessages(@PathVariable String title, @RequestParam(defaultValue = "0") int page) {
        return service.getAllMessages(title, page);
    }

    @PostMapping("/api/{title}/messages")
    public ResponseEntity<Object> sendMessageToChannel(@PathVariable String title,
                                                       @RequestBody Message message,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        service.addMessage(title, message, userDetails.getUser());
        return ResponseEntity.ok().build();
    }
}

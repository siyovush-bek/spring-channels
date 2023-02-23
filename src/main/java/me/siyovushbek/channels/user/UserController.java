package me.siyovushbek.channels.user;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;


@RestController
@CrossOrigin("*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("api/register")
    public ResponseEntity<Object> register(@RequestBody @Validated User user) {
        userService.addUser(user);
        URI location = URI.create("api/users/" + user.getUsername());
        return ResponseEntity.created(location).build();
    }

    @GetMapping("api/users/{username}")
    public User getUserDetails(@PathVariable String username) {
        return userService.getUserById(username);
    }

}

package me.siyovushbek.channels.api;

import me.siyovushbek.channels.user.User;
import me.siyovushbek.channels.user.UserController;
import me.siyovushbek.channels.user.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;


public class UserControllerTest {
    @Mock
    UserService service;

    UserController underTest;
    AutoCloseable closeable;
    User user;

    @BeforeEach
    void setUp() {
        user = new User("sm", "test_password");
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new UserController(service);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testRegister() {
        given(service.addUser(user)).willReturn(user);

        assertThat(underTest.register(user).getStatusCode())
                .isEqualTo(HttpStatus.CREATED);

    }

    @Test
    void testUserDetails() {
        given(service.getUserById("sm")).willReturn(user);

        assertThat(underTest.getUserDetails("sm"))
                .isEqualTo(user);
    }
}

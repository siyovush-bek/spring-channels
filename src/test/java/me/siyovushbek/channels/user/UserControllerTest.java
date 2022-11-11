package me.siyovushbek.channels.user;

import me.siyovushbek.channels.exception.UsernameAlreadyTakenException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;


class UserControllerTest {
    @Mock
    private UserService userService;

    @Mock
    User user;
    private UserController underTest;

    AutoCloseable closeable;

    @BeforeEach
    void startUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new UserController(userService);
    }

    @Test
    void itShouldRespondOk() {
        given(userService.addUser(user)).willReturn(user);
        assertThat(underTest.register(user).getStatusCode())
                .isEqualTo(HttpStatus.OK);
    }

    @Test
    void itShouldRespondBadRequest() {
        given(userService.addUser(user)).willThrow(UsernameAlreadyTakenException.class);
        assertThatThrownBy(() -> underTest.register(user))
                .isInstanceOf(UsernameAlreadyTakenException.class);
    }
}
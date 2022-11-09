package me.siyovushbek.channels.user;

import me.siyovushbek.channels.exception.UsernameAlreadyTakenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.BDDMockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder encoder;
    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Captor
    private ArgumentCaptor<String> passwordArgumentCaptor;
    private UserService underTest;

    String username = "test_user";
    User newUser = new User(username, "test_password");

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new UserService(userRepository, encoder);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldNotAddUser() {

        given(userRepository.existsById(username)).willReturn(true);

        assertThatThrownBy(() -> underTest.addUser(newUser))
                .isInstanceOf(UsernameAlreadyTakenException.class);

    }

    @Test
    void itShouldAddUser() {
        // given
        String encodedPassword = newUser.getPassword() + "encoded";
        given(userRepository.existsById(username)).willReturn(false);
        given(encoder.encode(newUser.getPassword())).willReturn(encodedPassword);

        // when
        underTest.addUser(newUser);

        // then
        then(encoder).should().encode(passwordArgumentCaptor.capture());
        assertThat(newUser.getPassword()).isEqualTo(encodedPassword);
        then(userRepository).should().save(userArgumentCaptor.capture());
    }

    @Test
    void itShouldNotLoadUser() {
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("User not found with name " + username);

    }

    @Test
    void itShouldLoadUser() {
        given(userRepository.findByUsername(username)).willReturn(Optional.of(newUser));

        assertThat(underTest.loadUserByUsername(username))
                .isInstanceOf(UserDetailsImpl.class);
    }
    
}
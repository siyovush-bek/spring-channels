package me.siyovushbek.channels.user;

import me.siyovushbek.channels.exception.UsernameAlreadyTakenException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    private UserService underTest;

    String username = "test_user";
    User newUser;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new UserService(userRepository, encoder);
        newUser = new User(username, "test_password");
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
        assertThat(newUser.getPassword()).isEqualTo(encodedPassword);
        then(userRepository).should().save(newUser);

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

    @Test
    void testLoadUserByUsernameWhenUserExists() {
        given(userRepository.findByUsername(username)).willReturn(Optional.of(newUser));

        assertThat(underTest.loadUserByUsername(username)).isInstanceOf(UserDetailsImpl.class);

    }

    @Test
    void testLoadUserByUsernameWhenUserDoesNotExist() {
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.loadUserByUsername(username)).isInstanceOf(UsernameNotFoundException.class);

    }
    
}
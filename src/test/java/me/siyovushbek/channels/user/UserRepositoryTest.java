package me.siyovushbek.channels.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;


@DataJpaTest
class UserRepositoryTest {
    @Autowired
    private UserRepository underTest;

    @Test
    void itShouldFindByUsername() {
        // given
        String username = "test_user";
        User user = new User(username, "test_password");
        underTest.save(user);

        // when
        Optional<User> foundUser = underTest.findByUsername(username);

        // then
        assertThat(foundUser.isPresent()).isTrue();
        assertThat(foundUser.get()).usingRecursiveComparison()
                .isEqualTo(user);
    }

    @Test
    void itShouldNotFindAnyUser() {
        // given
        String username = "you_will_never_guess";

        // when
        Optional<User> foundUser = underTest.findByUsername(username);

        // then
        assertThat(foundUser).isNotPresent();
    }
}
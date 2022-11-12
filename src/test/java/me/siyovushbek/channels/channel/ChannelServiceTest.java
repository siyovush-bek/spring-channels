package me.siyovushbek.channels.channel;

import me.siyovushbek.channels.exception.ChannelNotFoundException;
import me.siyovushbek.channels.exception.ChannelTitleAlreadyExistsException;
import me.siyovushbek.channels.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

class ChannelServiceTest {

    @Mock
    ChannelRepository repository;

    @Captor
    ArgumentCaptor<String> argumentCaptor;
    ChannelService underTest;
    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new ChannelService(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void itShouldReturnChannelFromDB() {
        // given
        String title = "myChannel";
        Channel channelFromDb = new Channel(title, new User());
        given(repository.findById(title)).willReturn(Optional.of(channelFromDb));

        // when
        Channel channel = underTest.findChannelById(title);

        // then
        Mockito.verify(repository).findById(argumentCaptor.capture());
        assertThat(title).isEqualTo(argumentCaptor.getValue());
        assertThat(channel).isEqualTo(channelFromDb);

    }

    @Test
    void itShouldThrowErrorAsChannelDoesNotExist() {
        // given
        String title = "myChannel";
        given(repository.findById(title)).willReturn(Optional.empty());

        // when and then
        assertThatThrownBy(() -> underTest.findChannelById(title))
                .isInstanceOf(ChannelNotFoundException.class);

        Mockito.verify(repository).findById(argumentCaptor.capture());
        assertThat(title).isEqualTo(argumentCaptor.getValue());
    }

    @Test
    void itShouldNotAddChannelAsItAlreadyExists() {
        // given
        ChannelRequest request = Mockito.mock(ChannelRequest.class);
        User user = Mockito.mock(User.class);
        given(request.getTitle()).willReturn("newChannel");
        given(repository.existsById(request.getTitle())).willReturn(true);

        // when && then
        assertThatThrownBy(() -> underTest.addChannel(request, user))
                .isInstanceOf(ChannelTitleAlreadyExistsException.class);
        Mockito.verify(request).getTitle();

    }

    // TODO: complete all tests
    @Test
    void itShouldAddChannelToDB() {
//        ChannelRequest request = Mockito.mock(ChannelRequest.class);
//        User user = Mockito.mock(User.class);
//        given(request.getTitle()).willReturn("newChannel");
//        given(repository.existsById(request.getTitle())).willReturn(false);
//        given(repository.save())
//
//        assertThat(underTest.addChannel(request, user).)
    }

    @Test
    void getChannelsList() {
    }

    @Test
    void addMember() {
    }
}
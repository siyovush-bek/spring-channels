package me.siyovushbek.channels.message;

import me.siyovushbek.channels.channel.Channel;
import me.siyovushbek.channels.channel.ChannelService;
import me.siyovushbek.channels.exception.UserNonMemberInChannelException;
import me.siyovushbek.channels.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


class MessageServiceTest {
    @Mock
    MessageRepository repository;

    @Mock
    ChannelService channelService;

    MessageService underTest;

    String username = "test_user";
    User user;
    AutoCloseable closeable;

    @Captor
    ArgumentCaptor<Pageable> pageArgumentCaptor;

    @Captor
    ArgumentCaptor<Channel> channelArgumentCaptor;

    @Captor
    ArgumentCaptor<Message> messageArgumentCaptor;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new MessageService(repository, channelService);
        user = new User(username, "test_password");
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testGetAllMessages() {
        Channel channel = new Channel("title", user);
        Page<Message> pageFromDb = Page.empty();
        given(channelService.findChannelById("title"))
                .willReturn(channel);
        given(repository.findMessageByChannel(any(), any()))
                .willReturn(Page.empty());
        Page<Message> methodResult = underTest.getAllMessages("title", 5);

        Mockito.verify(repository).findMessageByChannel(
                pageArgumentCaptor.capture(),
                channelArgumentCaptor.capture()
        );

        PageRequest request = (PageRequest) pageArgumentCaptor.getValue();
        Channel capturedChannel = channelArgumentCaptor.getValue();

        assertThat(request.getPageNumber()).isEqualTo(5);
        assertThat(request.getPageSize()).isEqualTo(20);
        assertThat(request.getSort()).usingRecursiveComparison()
                .isEqualTo(Sort.by(Sort.Direction.DESC, "sentAt"));

        assertThat(capturedChannel).isEqualTo(channel);
        assertThat(methodResult).isEqualTo(pageFromDb);
    }

    @Test
    void messageFromNonMemberShouldNotBeAdded() {
        String channelTitle = "channel1";
        User nonMember = new User("not_a_member", "test_pass");
        Channel channel = new Channel();
        channel.setAdmin(new User("sm", "pass"));
        given(channelService.findChannelById(channelTitle)).willReturn(channel);

        assertThatThrownBy(() ->
                underTest.addMessage(channelTitle, new Message("Hi"), nonMember))
                .isInstanceOf(UserNonMemberInChannelException.class);
    }

    @Test
    void messageFromMemberShouldBeAdded() {
        String channelTitle = "channel1";
        User member = new User("a_member", "test_pass");
        Channel channel = new Channel();
        channel.addMember(member);
        channel.setAdmin(new User("sm", "pass"));
        given(channelService.findChannelById(channelTitle)).willReturn(channel);

        underTest.addMessage(channelTitle, new Message("Hi"), member);

        Mockito.verify(repository).save(messageArgumentCaptor.capture());

        Message message = messageArgumentCaptor.getValue();

        assertThat(message.getBody())
                .isEqualTo("Hi");
        assertThat(message.getSenderName())
                .isEqualTo("a_member");
        assertThat(message.getChannel())
                .isEqualTo(channel);
        assertThat(message.getSentAt())
                .isNotNull();
    }

    @Test
    void messageFromAdminShouldBeAdded() {
        String channelTitle = "channel1";
        User admin = new User("very_admin", "test_pass");
        Channel channel = new Channel();
        channel.setAdmin(admin);
        given(channelService.findChannelById(channelTitle)).willReturn(channel);

        underTest.addMessage(channelTitle, new Message("Hi"), admin);

        Mockito.verify(repository).save(messageArgumentCaptor.capture());

        Message message = messageArgumentCaptor.getValue();

        assertThat(message.getBody())
                .isEqualTo("Hi");
        assertThat(message.getSenderName())
                .isEqualTo("very_admin");
        assertThat(message.getChannel())
                .isEqualTo(channel);
        assertThat(message.getSentAt())
                .isNotNull();
    }

}
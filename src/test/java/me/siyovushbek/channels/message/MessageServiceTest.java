package me.siyovushbek.channels.message;

import me.siyovushbek.channels.channel.ChannelService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class MessageServiceTest {
    @Mock
    MessageRepository repository;

    @Mock
    ChannelService channelService;

    MessageService underTest;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        underTest = new MessageService(repository, channelService);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }



}
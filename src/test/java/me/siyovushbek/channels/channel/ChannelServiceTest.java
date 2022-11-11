package me.siyovushbek.channels.channel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


class ChannelServiceTest {

    @Mock
    ChannelRepository repository;

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
    void findChannelById() {
    }

    @Test
    void addChannel() {
    }

    @Test
    void getChannelsList() {
    }

    @Test
    void addMember() {
    }
}
package me.siyovushbek.channels.message;

import me.siyovushbek.channels.channel.Channel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    Page<Message> findMessageByChannel(Pageable pageable, Channel channel);
}

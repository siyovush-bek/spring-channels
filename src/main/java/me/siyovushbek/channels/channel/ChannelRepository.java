package me.siyovushbek.channels.channel;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends PagingAndSortingRepository<Channel, String> {
}

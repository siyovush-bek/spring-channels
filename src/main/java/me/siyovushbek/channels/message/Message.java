package me.siyovushbek.channels.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.siyovushbek.channels.channel.Channel;
import me.siyovushbek.channels.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotBlank
    private String body;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    @JsonIgnore
    private Channel channel;

    @ManyToOne
    @JoinColumn(name = "username")
    @JsonIgnore
    private User sender;

    @Column(name = "sent_at")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY, value = "sent_at")
    private LocalDateTime sentAt;

    public Message(Long id, String body, User sender, LocalDateTime sentAt) {
        this.id = id;
        this.body = body;
        this.sender = sender;
        this.sentAt = sentAt;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @JsonProperty(value = "sender", access = JsonProperty.Access.READ_ONLY)
    public String getSenderName() {
        return this.sender.getUsername();
    }

}

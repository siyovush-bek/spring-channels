package me.siyovushbek.channels.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.siyovushbek.channels.channel.Channel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "chat_user")
public class User {

    @Id
    @NotBlank
    @Size(min = 6)
    private String username;

    @NotBlank
    @Size(min = 8)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @OneToMany(mappedBy = "admin")
    private Set<Channel> createdChannels;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ManyToMany(mappedBy = "members")
    private Set<Channel> joinedChannels;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Channel> getCreatedChannels() {
        return createdChannels;
    }

    public void setCreatedChannels(Set<Channel> createdChannels) {
        this.createdChannels = createdChannels;
    }

    public Set<Channel> getJoinedChannels() {
        return joinedChannels;
    }

    public void setJoinedChannels(Set<Channel> joinedChannels) {
        this.joinedChannels = joinedChannels;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdChannels=" + createdChannels +
                ", joinedChannels=" + joinedChannels +
                '}';
    }
}

package me.siyovushbek.channels.channel;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.siyovushbek.channels.message.Message;
import me.siyovushbek.channels.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "channel")
public class Channel {
    @Id
    @NotBlank
    @Pattern(regexp = "\\w{3,15}")
    @Column(unique = true)
    private String title;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "admin")
    @NotNull
    private User admin;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "channel_member",
            joinColumns = @JoinColumn(name = "channel_id"),
            inverseJoinColumns = @JoinColumn(name = "username"))
    private Set<User> members = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "channel")
    List<Message> messages = new ArrayList<>();
    public Channel(String title, User admin) {
        this.title = title;
        this.admin = admin;
    }

    public Channel(){}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public Set<User> getMembers() {
        return members;
    }

    public void setMembers(Set<User> members) {
        this.members = members;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @JsonProperty(value = "admin", access = JsonProperty.Access.READ_ONLY)
    public String createdBy() {
        return admin.getUsername();
    }

    @JsonProperty(value = "members", access = JsonProperty.Access.READ_ONLY)
    public List<String> getMembersList() {
        return members.stream()
                .map(User::getUsername)
                .toList();
    }

}

package me.siyovushbek.channels.channel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ChannelRequest {
    @NotBlank
    @Size(min = 3)
    private String title;

    ChannelRequest(){}

    public ChannelRequest(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package me.siyovushbek.channels.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Only members of channel can send messages to a channel")
public class UserNonMemberInChannelException extends RuntimeException{
}

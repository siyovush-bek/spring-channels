package me.siyovushbek.channels.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Title already taken")
public class ChannelTitleAlreadyExistsException extends RuntimeException{

}

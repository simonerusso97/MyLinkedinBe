package it.unisalento.mylinkedin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Post non trovato")
public class PostNotFoundException extends Exception{

}

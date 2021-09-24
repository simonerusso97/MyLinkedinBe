package it.unisalento.mylinkedin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Qualcosa non ha funzionato")
public class OperationFailedException extends Exception {

}

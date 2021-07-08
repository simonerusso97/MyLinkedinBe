package it.unisalento.mylinkedin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Operazione fallita")
public class OperationFailedException extends Exception {

}

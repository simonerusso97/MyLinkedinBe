package it.unisalento.mylinkedin.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Società già registrata")
public class CompanyAlreadyExist extends Exception {

}

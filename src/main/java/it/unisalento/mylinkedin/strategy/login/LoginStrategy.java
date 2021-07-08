package it.unisalento.mylinkedin.strategy.login;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.dto.UserDTO;
import it.unisalento.mylinkedin.iService.IUserService;

public interface LoginStrategy {
	public UserDTO login(User user, IUserService userService) throws FileNotFoundException, IOException, ParseException;
}

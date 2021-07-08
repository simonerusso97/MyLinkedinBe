package it.unisalento.mylinkedin.strategy.login;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.UserDTO;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.IUserService;
import it.unisalento.mylinkedin.strategy.post.GetPostStrategy;

public class LoginContext {
	
	private LoginStrategy loginStrategy;
	
	public LoginContext(LoginStrategy loginStrategy) {
		this.loginStrategy = loginStrategy;
	}
	
	public UserDTO login(User user, IUserService userService) throws FileNotFoundException, IOException, ParseException {
		return loginStrategy.login(user, userService);
	}
	
	
	
}

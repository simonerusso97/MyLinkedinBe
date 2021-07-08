package it.unisalento.mylinkedin.strategy.post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.IUserService;

public interface GetPostStrategy {
	public List<PostDTO> getAllPost(IPostService postService, IUserService userService) throws FileNotFoundException, IOException, ParseException, UserNotFoundException;
}

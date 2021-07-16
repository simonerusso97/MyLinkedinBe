package it.unisalento.mylinkedin.strategy.post;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.IAttachedService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.IUserService;

public class PostContext {
	private GetPostStrategy getPostStrategy;
	
	public PostContext(GetPostStrategy getPostStrategy) {
		this.getPostStrategy = getPostStrategy;
	}
	
	public List<PostDTO> getAllPost(IPostService postService, IUserService userService, IAttachedService attachedService) throws FileNotFoundException, IOException, ParseException, UserNotFoundException{
		return getPostStrategy.getAllPost(postService, userService, attachedService);
	}
}

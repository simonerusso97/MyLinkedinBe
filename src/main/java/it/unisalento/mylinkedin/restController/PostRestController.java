package it.unisalento.mylinkedin.restController;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.IAttachedService;
import it.unisalento.mylinkedin.iService.ICommentService;
import it.unisalento.mylinkedin.iService.ICompanyService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.ISkillService;
import it.unisalento.mylinkedin.iService.IStructureService;
import it.unisalento.mylinkedin.iService.IUserService;
import it.unisalento.mylinkedin.strategy.post.PostAdminImpl;
import it.unisalento.mylinkedin.strategy.post.PostApplicantImpl;
import it.unisalento.mylinkedin.strategy.post.PostContext;
import it.unisalento.mylinkedin.strategy.post.PostOfferorImpl;

@RestController
@RequestMapping("/post")
public class PostRestController {
	
	@Autowired
	IPostService postService;
	
	@Autowired
	ICompanyService companyService;
	
	@Autowired
	ISkillService skillService;
	
	@Autowired
	IStructureService structureService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	ICommentService commentService;
	
	@Autowired
	IAttachedService attachedService;

	@RequestMapping(value="/findAllPost/{userType}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PostDTO> findAll(@PathVariable("userType") String userType) throws IOException, ParseException, UserNotFoundException{
		PostContext postContext;
		if(userType.equals("admin")) {
			 postContext = new PostContext(new PostAdminImpl());
		}
		else if(userType.equals("offeror")) {
			 postContext = new PostContext(new PostOfferorImpl());
		}
		else {
			 postContext = new PostContext(new PostApplicantImpl());
		}
		List<PostDTO> list = postContext.getAllPost(postService, userService, attachedService);
		return list;
	}
	
	@RequestMapping(value="/changePostVisibility", method = RequestMethod.PATCH, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostDTO> hideShowPost(@RequestBody @Valid PostDTO postDTO) throws OperationFailedException{
		
		try {
			Post post = postService.findById(postDTO.getId());
			post.setHide(postDTO.isHide());
			postService.save(post);
		}catch (Exception e) {
			if(e.getClass()==OperationFailedException.class) {
				return new ResponseEntity<PostDTO>(HttpStatus.NO_CONTENT);
			}
		}		
		return new ResponseEntity<PostDTO>(HttpStatus.OK);
		
	}
}

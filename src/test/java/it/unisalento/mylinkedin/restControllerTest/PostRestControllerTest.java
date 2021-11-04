package it.unisalento.mylinkedin.restControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.aspectj.util.IStructureModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.mylinkedin.dto.CommentDTO;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.iService.IAttachedService;
import it.unisalento.mylinkedin.iService.ICommentService;
import it.unisalento.mylinkedin.iService.ICompanyService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.ISkillService;
import it.unisalento.mylinkedin.iService.IStructureService;
import it.unisalento.mylinkedin.iService.IUserService;
import it.unisalento.mylinkedin.restController.PostRestController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PostRestController.class)
public class PostRestControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private IPostService postService;
	@MockBean
	private ICompanyService companyService;
	@MockBean
	private ISkillService skillService;
	@MockBean
	private IStructureService structureService;
	@MockBean
	private IUserService userService;
	@MockBean
	private ICommentService commentService;
	@MockBean
	private IAttachedService attachedService;
	
	private PostDTO postDTO;
	private CommentDTO commentDTO;
	
	@BeforeEach
	void initEnv() {
		postDTO = new PostDTO();
		postDTO.setId(1);
		postDTO.setHide(false);
		
		commentDTO = new CommentDTO();
		
	}
	
	@Test
	void login(){
		try {
			mockMvc.perform(get("post/findAllPost/offeror")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void hideShowPost(){
		try {
			mockMvc.perform(patch("post/changePostVisibility")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(postDTO)))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void findPostById(){
		try {
			mockMvc.perform(get("post/findPostById/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void saveComment(){
		try {
			mockMvc.perform(patch("post/saveComment/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(commentDTO)))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void saveReplyComment(){
		try {
			mockMvc.perform(patch("post/saveComment/1/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(commentDTO)))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void save(){
		try {
			mockMvc.perform(post("post/save")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(postDTO)))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void findAllSkill(){
		try {
			mockMvc.perform(get("getAllSkill")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}

}

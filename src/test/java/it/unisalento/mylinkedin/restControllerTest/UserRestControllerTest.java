package it.unisalento.mylinkedin.restControllerTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.MessageDTO;
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.IUserService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserRestControllerTest.class)
public class UserRestControllerTest {


	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	private ApplicantDTO applicantDTOFrontEnd;
	private RegularDTO regularDTOFE;
	private OfferorDTO offerorDTOFE;
	private PostDTO postDTO;
	private List<PostDTO> postList;
	private MessageDTO messageDTO;
	
	@BeforeEach
	void initEnvTest() {
		applicantDTOFrontEnd = new ApplicantDTO();
		applicantDTOFrontEnd.setName("Simone");
		applicantDTOFrontEnd.setSurname("Russo");	
		
		regularDTOFE = new RegularDTO();
		regularDTOFE.setName("Simone");
		regularDTOFE.setSurname("Russo");
		regularDTOFE.setAddress("Lecce");
		regularDTOFE.setBanned(false);
		regularDTOFE.setId(1);
		regularDTOFE.setDisabled(false);
		
		offerorDTOFE = new OfferorDTO();
		offerorDTOFE.setName("Simone");
		offerorDTOFE.setSurname("Russo");
		offerorDTOFE.setVerified(false);
		
		postList = new ArrayList<>();
		postDTO = new PostDTO();
		postDTO.setId(1);
		postList.add(postDTO);
		postDTO = new PostDTO();
		postDTO.setId(2);
		postList.add(postDTO);
		postDTO = new PostDTO();
		postDTO.setId(3);
		postList.add(postDTO);
		
		messageDTO = new MessageDTO();
		

	}
	
	@Test
	void loginTest() {
		String email = "prova@gmail.com";
		String pwd = "1997";
		
			try {
				mockMvc.perform(get("user/login/" +  email + "/" + pwd)
						.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		
	}
	
	@Test
	void findAllRegistrationRequestTest() {
		try {
			mockMvc.perform(get("findAllRegistrationRequest")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void acceptUserTest() {
		try {
			mockMvc.perform(patch("user/applicatSignUp")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(regularDTOFE)))
			.andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void getAllDisabledUserTest() {
		try {
			mockMvc.perform(get("user/getAllDisabledUser")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	@Test
	void banUnbanTest() {
		try {
			mockMvc.perform(patch("user/banUnban")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(regularDTOFE)))
			.andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	
	@Test
	void registrationApplicantTest() {
		try {
			mockMvc.perform(post("user/applicatSignUp")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(applicantDTOFrontEnd)))
			.andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	@Test
	void registrationOfferor() {
		try {
			mockMvc.perform(post("user/offerorSignUp")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(offerorDTOFE)))
			.andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void findOfferorNotVerifed() {
		try {
			mockMvc.perform(get("user/findOfferorNotVerifed/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	void acceptOfferor() {
		try {
			mockMvc.perform(patch("user/acceptOfferor")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(offerorDTOFE)))
			.andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}	
	@Test
	void savePost() {
		try {
			mockMvc.perform(post("user/updateInterestedList/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(postList)))
			.andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void findAllInterestedPost() {
		try {
			mockMvc.perform(get("user/findAllInterestedPost/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void getAllMessage() {
		try {
			mockMvc.perform(get("user/getAllMessage/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void getAllUser() {
		try {
			mockMvc.perform(get("user/getAllUser")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	void sendMessage() {
		try {
			mockMvc.perform(post("user/sendMessage")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(messageDTO)))
			.andExpect(status().isOk());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}

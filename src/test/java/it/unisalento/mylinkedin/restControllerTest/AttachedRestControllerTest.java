package it.unisalento.mylinkedin.restControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.MessageDTO;
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.iService.IAttachedService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.restController.AttachedRestController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AttachedRestController.class)
public class AttachedRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private IAttachedService attachedServiceMock;
	@MockBean
	private IPostService postServiceMock;
	
	private MultipartFile file;
	
	@BeforeEach
	void initEnv() {
	}
	
	@Test
	void upload() {
			try {
				mockMvc.perform(post("api/attachment/uploadFile/prova/pdf")
						.contentType(MediaType.APPLICATION_JSON_VALUE)
						.content(mapper.writeValueAsString(file)))
				.andExpect(status().isOk());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	@Test
	void getAttached() {
		try {
			mockMvc.perform(get("api/attachment/getAttached")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

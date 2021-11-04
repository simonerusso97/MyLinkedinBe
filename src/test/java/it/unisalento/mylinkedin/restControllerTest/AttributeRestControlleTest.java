package it.unisalento.mylinkedin.restControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import it.unisalento.mylinkedin.dto.AttributeDTO;
import it.unisalento.mylinkedin.iService.IAttributeService;
import it.unisalento.mylinkedin.restController.AttributeRestController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AttributeRestController.class)
public class AttributeRestControlleTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private IAttributeService attributeService;
	
	private AttributeDTO attributeDTO;
	private AttributeDTO newAttributeDTO;

	
	@BeforeEach
	void initEnv() {
		attributeDTO = new AttributeDTO();
		attributeDTO.setId(1);
		
		newAttributeDTO = new AttributeDTO();
		newAttributeDTO.setDeletable(false);
		newAttributeDTO.setName("prova");
		newAttributeDTO.setType("String");
	}
	
	
	@Test
	void findAllAttribute() {
		try {
			mockMvc.perform(get("attribute/findAllAttribute")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void updateAttribute() {
		try {
			mockMvc.perform(patch("attribute/updateAttribute")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(attributeDTO)))
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
	void createNewAttribute() {
		try {
			mockMvc.perform(post("attribute/createAttribute")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(newAttributeDTO)))
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
	void deleteAttribure() {
		try {
			mockMvc.perform(delete("attribute/deleteAttribute/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
}

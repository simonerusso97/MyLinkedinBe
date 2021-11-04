package it.unisalento.mylinkedin.restControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.mylinkedin.dto.StructureDTO;
import it.unisalento.mylinkedin.iService.IAttributeService;
import it.unisalento.mylinkedin.iService.IStructureService;
import it.unisalento.mylinkedin.restController.PostRestController;
import it.unisalento.mylinkedin.restController.StructureRestController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = StructureRestController.class)
public class StructureRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private IStructureService structureService;
	@MockBean
	private IAttributeService attributeService;
	
	private StructureDTO structureDTO;
	private StructureDTO newStructureDTO;

	
	@BeforeEach
	void initEnv() {
		structureDTO = new StructureDTO();
		structureDTO.setId(1);
		
		newStructureDTO = new StructureDTO();
	}
	
	@Test
	void getAllStructure(){
		try {
			mockMvc.perform(get("structure/getAll")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void deleteTest() {
		try {
			mockMvc.perform(delete("attribute/delete/1")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void update(){
		try {
			mockMvc.perform(patch("structure/update")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(structureDTO)))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void createStructure(){
		try {
			mockMvc.perform(patch("structure/createStructure")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(newStructureDTO)))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void getByType(){
		try {
			mockMvc.perform(get("structure/getByType/offeror")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	
}

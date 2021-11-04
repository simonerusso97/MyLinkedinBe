package it.unisalento.mylinkedin.restControllerTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.iService.ICompanyService;
import it.unisalento.mylinkedin.restController.CompanyRestController;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CompanyRestController.class)
public class CompanyRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private ICompanyService companyService;
	
	private CompanyDTO companyDTO;
	
	@BeforeEach
	void initEnv() {
		companyDTO = new CompanyDTO();
	
	}
	
	@Test
	void login(){
		try {
			mockMvc.perform(get("company/login/prova@gmail.com/1234")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void findAll(){
		try {
			mockMvc.perform(get("company/findAll")
					.contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
	@Test
	void registrationCompany(){
		try {
			mockMvc.perform(post("company/companySignUp")
					.contentType(MediaType.APPLICATION_JSON_VALUE)
					.content(mapper.writeValueAsString(companyDTO)))
			.andExpect(status().isOk());
		} catch (Exception e) {
			// TODO Auto-generated catch block	
		}
	}
	
}

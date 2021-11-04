package it.unisalento.mylinkedin.iService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.exceptions.CompanyAlreadyExist;
import it.unisalento.mylinkedin.exceptions.CompanyNotFound;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CompanyServiceTest {

	@Autowired
	private ICompanyService companyService;
	
	@Mock
	private ICompanyService companyServiceMock;
	
	private Company newCompany;
	private Company company;

	@BeforeEach
	void initEnv() {

		this.company = new Company();
		this.company.setId(1);
		this.company.setName("prova");
		this.company.setPassword("prova");
		
		this.newCompany = new Company();		
		when(companyServiceMock.save(newCompany)).thenReturn(newCompany);

	}
	
	@Test
	void findById() {
			try {
				assertThat(companyService.findById(1)).isNotNull();
			} catch (CompanyNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	@Test
	void findByNameAndPassword() {
			assertThat(companyService.findByNameAndPassword("prova","prova")).isNotNull();
	}
	
	@Test
	void findAll() {
			assertThat(companyServiceMock.findAll()).isNotNull();

	}
	
	@Test
	void findByName() {
		try {
			assertThat(companyService.findByName("prova")).isNull();
		} catch (CompanyAlreadyExist e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	@Test
	void save() {
		int id = companyServiceMock.save(newCompany).getId();
		assertThat(id).isEqualTo(0);	
		}
	
	
	
	
}

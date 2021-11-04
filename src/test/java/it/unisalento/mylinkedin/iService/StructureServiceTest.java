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

import it.unisalento.mylinkedin.domain.entity.Structure;
import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;
import it.unisalento.mylinkedin.exceptions.StructureNotFound;
import it.unisalento.mylinkedin.serviceImpl.StructureService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StructureServiceTest {
	
	@Autowired
	private IStructureService structureService;
	
	@Mock
	private IStructureService structureServiceMock;
	
	private Structure structure;
	private StructureHasAttribute shaList;
	
	@BeforeEach
	void initEnv() {
		this.structure = new Structure();
		
		when(structureServiceMock.save(structure)).thenReturn(structure);
	}
	
	@Test
	void findByUserType() {
		assertThat(structureServiceMock.findByUserType("offeror")).isNotNull();
	}
	
	@Test
	void findAll() {
		assertThat(structureServiceMock.findAll()).isNotNull();

	}
	
	void getById() {
		try {
			assertThat(structureServiceMock.getById(0)).isNotNull();
		} catch (StructureNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	void save() {
		int id = structureServiceMock.save(structure).getId();
		assertThat(id).isEqualTo(0);	
		}
	
}

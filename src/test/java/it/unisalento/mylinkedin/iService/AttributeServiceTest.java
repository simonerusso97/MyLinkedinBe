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

import it.unisalento.mylinkedin.domain.entity.Attribute;
import it.unisalento.mylinkedin.exceptions.AttributeNotFoundException;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AttributeServiceTest {
	
	@Autowired
	private IAttributeService attributeService;
	
	@Mock
	private IAttributeService attributeServiceMock;
	
	private Attribute attribute;
	private Attribute newAttribute;
	
	@BeforeEach
	void initEnv() {
		this.attribute = new Attribute();
		this.attribute.setId(0);
		
		this.newAttribute = new Attribute();
		when(attributeServiceMock.save(newAttribute)).thenReturn(newAttribute);


	}
	
	@Test
	void findAllAttribute() {
			assertThat(attributeServiceMock.findAllAttribute()).isNotNull();

	}
	
	@Test
	void findById() {
			try {
				assertThat(attributeService.findById(1)).isNotNull();
			} catch (AttributeNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	@Test
	void save() {
		int id = attributeServiceMock.save(newAttribute).getId();
		assertThat(id).isEqualTo(0);	
		}
	
	@Test
	void findByStructureId() {
			assertThat(attributeServiceMock.findByStructureId(0)).isNotNull();

	}
}

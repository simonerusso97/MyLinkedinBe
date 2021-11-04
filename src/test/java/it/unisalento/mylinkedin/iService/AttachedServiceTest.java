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

import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AttachedServiceTest {
	
	@Autowired
	private IAttachedService attachedService;
	
	@Mock
	private IAttachedService attachedServiceMock;
	
	private Attached attached;

	@BeforeEach
	void initEnv() {
		this.attached = new Attached();
		this.attached.setName("prova");
		
		when(attachedServiceMock.save(attached)).thenReturn(attached);
		

	}
	@Test
	void save() {
		int id = attachedServiceMock.save(attached).getId();
		assertThat(id).isEqualTo(0);	
		}

	@Test
	void findByPostIdAndType() {
		
			assertThat(attachedServiceMock.findByPostIdAndType(1, "PDF")).isNotNull();

	}
	
	
	@Test
	void findById() {
			try {
				assertThat(attachedService.findById(1)).isNotNull();
			} catch (OperationFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	
	@Test
	void findByIdPost() {
		assertThat(attachedServiceMock.findByIdPost(0)).isNotNull();
	}
}

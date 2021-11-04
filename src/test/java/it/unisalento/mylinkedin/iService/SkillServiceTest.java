package it.unisalento.mylinkedin.iService;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.unisalento.mylinkedin.domain.entity.Skill;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.SkillNotFound;
import it.unisalento.mylinkedin.serviceImpl.SkillService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SkillServiceTest {
	
	@Autowired
	private SkillService skillService;
	
	@Mock
	private SkillService skillServiceMock;
	
	private Skill skill;
	
	@BeforeEach
	void initEnv() {
		this.skill = new Skill();
		this.skill.setId(1);
	}

	@Test
	void findByPostIdAndType() {
		
			assertThat(skillServiceMock.findAll()).isNotNull();

	}
	
	
	@Test
	void findById() {
			try {
				assertThat(skillService.findById(1)).isNotNull();
			} catch (SkillNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}

package it.unisalento.mylinkedin.iService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import it.unisalento.mylinkedin.domain.entity.Message;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IUserServiceTest {
	
	@Autowired
	private IUserService userService;
	
	@Mock
	private IUserService userServiceMock;
	
	private User user;
	private Regular frontEndUser;
	private Message message;
	List<RegularInterestedInPost> updatedList;
	@BeforeEach
	void initTestEnv() {
		this.user = new User();
		this.user.setId(1);
		this.user.setName("Simone");
		this.user.setEmail("prova@gmail.com");
		
		this.frontEndUser = new Regular();
		this.frontEndUser.setName("Simone");
		this.frontEndUser.setSurname("Russo");
		this.frontEndUser.setEmail("97.russo@gamil.com");
		this.frontEndUser.setBirthDate(new Date());
		
		this.message = new Message();
		
		this.updatedList = new ArrayList<RegularInterestedInPost>();
		
		try {
			when(userServiceMock.save(frontEndUser)).thenReturn(frontEndUser);
		}catch (Exception e) {
			// TODO: handle exception
		}
		when(userServiceMock.saveMessage(message)).thenReturn(message);
		when(userServiceMock.updateInterestedList(updatedList)).thenReturn(updatedList);

	}

	@Test
	void findAllUserTest() {
		assertThat(userService.findAllUser()).isNotNull();
	}
	
	@Test
	void findUserByIdTest() {
		try {
			assertThat(userService.findById(1)).isNotNull();
			assertThat(userService.findById(1).getName()).isEqualTo(user.getName());

		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void getByIdThrowsExTest() {
		Exception ex = assertThrows(UserNotFoundException.class, () -> {
			userService.findById(0);
		});
		assertThat(ex).isNotNull();
	}
	
	@Test
	void saveTest() {
		int id = userServiceMock.save(frontEndUser).getId();
		assertThat(id).isEqualTo(0);
	}
	
	@Test
	void findByEmailAndPassword() {
		try {
			assertThat(userService.findByEmailAndPassword("prova@gmail.com", "1997")).isNotNull();

		} catch (UserNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void findRegularByDisabled() {
		assertThat(userServiceMock.findRegularByDisabled(true)).isNotNull();
	}
	
	@Test
	void findById() {
		try {
			assertThat(userService.findById(1)).isNotNull();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void findByEmail() {
		assertThat(userService.findByEmail("prova@gmail.com")).isNotNull();
	}
	
	@Test
	void findByCompanyIdAndVerified() {
		assertThat(userServiceMock.findByCompanyIdAndVerified(0, true)).isNotNull();
	}
	
	
	@Test
	void findInterestedPostByUserId() {
		assertThat(userServiceMock.findInterestedPostByUserId(0)).isNotNull();
	}
	
	@Test
	void updateInterestedList() {
		assertThat(userServiceMock.updateInterestedList(updatedList)).isNotNull();
	}
	
	@Test
	void findMessageByUserId() {
		assertThat(userServiceMock.findMessageByUserId(0)).isNotNull();
	}
	
	@Test
	void findUserById() {
		try {
			assertThat(userService.findUserById(0)).isNotNull();
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void saveMessage() {
		assertThat(userServiceMock.saveMessage(message).getId()).isEqualTo(0);
	}
}

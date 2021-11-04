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

import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.exceptions.AttributeNotFoundException;
import it.unisalento.mylinkedin.exceptions.CommentNotFoundException;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CommentServiceTest {
	@Autowired
	private ICommentService commentService;
	
	@Mock
	private ICommentService commentServiceMock;
	
	private Comment newComment;
	private Comment comment;

	
	@BeforeEach
	void initEnv() {
		this.newComment = new Comment();
		when(commentServiceMock.save(newComment)).thenReturn(newComment);

	}
	
	@Test
	void save() {
		int id = commentServiceMock.save(newComment).getId();
		assertThat(id).isEqualTo(0);	
		}
	
	@Test
	void findById() {
			try {
				assertThat(commentService.findById(1)).isNotNull();
			} catch (CommentNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	@Test
	void findChild() {
			assertThat(commentServiceMock.findChild(1)).isNotNull();

	}
	
}

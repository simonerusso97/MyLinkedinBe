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
import it.unisalento.mylinkedin.domain.entity.JsonDocument;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostServiceTest {
	
	@Autowired
	private IPostService postService;
	
	@Mock
	private IPostService postServiceMock;
	
	private Post newPost;
	private Post post;

	private JsonDocument jDoc;
	@BeforeEach
	void initEnv() {
		this.newPost = new Post();
		this.newPost.setName("prova");
		
		this.post = new Post();
		this.post.setId(0);
		
		jDoc = new JsonDocument();
		when(postServiceMock.save(newPost)).thenReturn(newPost);
		when(postServiceMock.saveJsonDocument(jDoc)).thenReturn(jDoc);

		

	}
	@Test
	void save() {
		int id = postServiceMock.save(newPost).getId();
		assertThat(id).isEqualTo(0);	
		}

	@Test
	void findById() {
		
			try {
				assertThat(postService.findById(1)).isNotNull();
			} catch (PostNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
	
	@Test
	void findAll() {
		
			assertThat(postServiceMock.findAll()).isNotNull();

	}
	
	
	@Test
	void findInterestedPost() {
		
			assertThat(postServiceMock.findInterestedPost(1)).isNotNull();

	}
	
	@Test
	void getLastJsonDucumentIndex() {
		
			assertThat(postServiceMock.getLastJsonDucumentIndex()).isNotNull();

	}
	
	@Test
	void saveJsonDocument() {
		
			assertThat(postServiceMock.saveJsonDocument(jDoc)).isNotNull();

	}


}

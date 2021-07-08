package it.unisalento.mylinkedin.domain.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class JsonDocument {
	
	

public JsonDocument() {}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	String name;
	
	@OneToOne
	Post post;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}	
}

package it.unisalento.mylinkedin.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
public class Attached {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	@Column(unique=true)
	String name;
	String type;
	
	@ManyToOne
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	
}

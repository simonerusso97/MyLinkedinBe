package it.unisalento.mylinkedin.domain.relationship;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Skill;

@Entity
public class PostRequireSkill {
	
	
	public PostRequireSkill() {
	}

	@GeneratedValue
	@Id
	int id;
	
	@ManyToOne()
	Skill skill;
	
	@ManyToOne
	Post post;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	

}
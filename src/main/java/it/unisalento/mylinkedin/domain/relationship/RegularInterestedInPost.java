package it.unisalento.mylinkedin.domain.relationship;

import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;


@Entity
public class RegularInterestedInPost{


	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	
	@ManyToOne
	Regular regular;
	
	@ManyToOne
	Post post;
	
	boolean notified;

	

	public Regular getRegular() {
		return regular;
	}

	public void setRegular(Regular regular) {
		this.regular = regular;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isNotified() {
		return notified;
	}

	public void setNotified(boolean notified) {
		this.notified = notified;
	}
	
	
}
package it.unisalento.mylinkedin.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;

@Entity
@DiscriminatorValue("regular")
public class Regular extends User{
	
	
	public Regular() {
	}
	String address;	
	String degree;
	String token;
	
	@Column(columnDefinition = "bit(1) default 0")
	boolean banned;
	@Column(columnDefinition = "bit(1) default 1")
	boolean disabled;
	
	@OneToMany(mappedBy = "createdBy", targetEntity = Post.class, cascade = CascadeType.ALL, orphanRemoval=true, fetch = FetchType.LAZY)
	List<Post> postList;
	
	@OneToMany(mappedBy = "regular", targetEntity = RegularInterestedInPost.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<RegularInterestedInPost> interestedPostList;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public List<Post> getPostList() {
		return postList;
	}

	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}

	public List<RegularInterestedInPost> getRegularInterestedInPostList() {
		return interestedPostList;
	}

	public void setRegularInterestedInPostList(List<RegularInterestedInPost> regularInterestedInPostList) {
		this.interestedPostList = regularInterestedInPostList;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}


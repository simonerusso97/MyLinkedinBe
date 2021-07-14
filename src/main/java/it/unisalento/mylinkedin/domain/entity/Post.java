package it.unisalento.mylinkedin.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;

@Entity
public class Post {
	
public Post() {}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	String name;
	Date pubblicationDate;
	boolean hide;
	
	@OneToOne(mappedBy = "post", targetEntity = JsonDocument.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	JsonDocument jsonDocument;
	
	@ManyToOne
	Structure structure;
	
	@ManyToOne
	Regular createdBy;
	
	@OneToMany(mappedBy = "post", targetEntity = Comment.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<Comment> commentList;
	
	@OneToMany(mappedBy = "post", targetEntity = PostRequireSkill.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<PostRequireSkill> postRequireSkillList;
	
	@OneToMany(mappedBy = "post", targetEntity = RegularInterestedInPost.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<RegularInterestedInPost> regularInterestedInPost;
	
	@OneToMany(mappedBy = "post", targetEntity = Attached.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<Attached> attachedList;

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

	public Date getPubblicationDate() {
		return pubblicationDate;
	}

	public void setPubblicationDate(Date pubblicationDate) {
		this.pubblicationDate = pubblicationDate;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public JsonDocument getJsonDocument() {
		return jsonDocument;
	}

	public void setJsonDocument(JsonDocument jsonDocument) {
		this.jsonDocument = jsonDocument;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Regular getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Regular createdBy) {
		this.createdBy = createdBy;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}


	public List<PostRequireSkill> getPostRequireSkillList() {
		return postRequireSkillList;
	}

	public void setPostRequireSkillList(List<PostRequireSkill> postRequireSkillList) {
		this.postRequireSkillList = postRequireSkillList;
	}

	public List<RegularInterestedInPost> getRegularInterestedInPost() {
		return regularInterestedInPost;
	}

	public void setRegularInterestedInPost(List<RegularInterestedInPost> regularInterestedInPost) {
		this.regularInterestedInPost = regularInterestedInPost;
	}

	public List<Attached> getAttachedList() {
		return attachedList;
	}

	public void setAttachedList(List<Attached> attachedList) {
		this.attachedList = attachedList;
	}
	
	
	

}



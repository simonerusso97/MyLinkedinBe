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
@Entity
public class Comment {
	
	

	public Comment() {}
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	Date date;
	String text;
	@ManyToOne
	Comment parent;
	
	@ManyToOne
	Applicant applicant;
	@ManyToOne
	Post post;
	
	@OneToMany(mappedBy = "parent", targetEntity = Comment.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<Comment> answerList;
	

	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Applicant getApplicant() {
		return applicant;
	}
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	public Comment getParent() {
		return parent;
	}
	public void setParent(Comment parent) {
		this.parent = parent;
	}
	public List<Comment> getAnswerList() {
		return answerList;
	}
	public void setAnswerList(List<Comment> answerList) {
		this.answerList = answerList;
	}
	
	
	
	
}

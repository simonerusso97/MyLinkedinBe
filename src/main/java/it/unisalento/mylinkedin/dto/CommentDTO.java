package it.unisalento.mylinkedin.dto;

import java.util.Date;
import java.util.List;

public class CommentDTO {
	
	int id;
	Date date;
	String text;
	ApplicantDTO applicant;
	
	//TODO: forse non serve
	List<CommentDTO> answerList;
	
	public CommentDTO() {}

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

	public ApplicantDTO getApplicant() {
		return applicant;
	}

	public void setApplicant(ApplicantDTO applicant) {
		this.applicant = applicant;
	}

	public List<CommentDTO> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<CommentDTO> answerList) {
		this.answerList = answerList;
	}
	
	
	
	

}

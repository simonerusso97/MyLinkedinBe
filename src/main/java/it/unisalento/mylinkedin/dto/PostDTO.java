package it.unisalento.mylinkedin.dto;

import java.util.Date;
import java.util.List;

public class PostDTO {
	
	int id;
	boolean hide;
	Date pubblicationDate;
	String name;
	StructureDTO structure;
	List<JsonDocumentDTO> jsonDocument;
	List<RegularDTO> interestedUserList;
	List<SkillDTO> skillList;
	RegularDTO createdBy;
	List<CommentDTO> commentList;
	
	public PostDTO() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isHide() {
		return hide;
	}

	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public Date getPubblicationDate() {
		return pubblicationDate;
	}

	public void setPubblicationDate(Date pubblicationDate) {
		this.pubblicationDate = pubblicationDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StructureDTO getStructure() {
		return structure;
	}

	public void setStructure(StructureDTO structure) {
		this.structure = structure;
	}

	public List<JsonDocumentDTO> getJsonDocument() {
		return jsonDocument;
	}

	public void setJsonDocument(List<JsonDocumentDTO> jsonDocument) {
		this.jsonDocument = jsonDocument;
	}


	public List<RegularDTO> getInterestedUserList() {
		return interestedUserList;
	}

	public void setInterestedUserList(List<RegularDTO> interestedUserList) {
		this.interestedUserList = interestedUserList;
	}

	public List<SkillDTO> getSkillList() {
		return skillList;
	}

	public void setSkillList(List<SkillDTO> skillList) {
		this.skillList = skillList;
	}

	public RegularDTO getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(RegularDTO createdBy) {
		this.createdBy = createdBy;
	}

	public List<CommentDTO> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<CommentDTO> commentList) {
		this.commentList = commentList;
	}

	
	

}

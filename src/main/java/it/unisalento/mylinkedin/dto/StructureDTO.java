package it.unisalento.mylinkedin.dto;

import java.util.List;


public class StructureDTO {
	
	int id;
	String name;
	String description; 
	String userType;
	boolean deletable;
	List<AttributeDTO> attributeList;
	
	public StructureDTO() {}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public List<AttributeDTO> getAttributeList() {
		return attributeList;
	}

	public void setAttributeList(List<AttributeDTO> attributeList) {
		this.attributeList = attributeList;
	}
	
	
}

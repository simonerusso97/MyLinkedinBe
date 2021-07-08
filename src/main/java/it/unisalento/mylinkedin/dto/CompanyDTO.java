package it.unisalento.mylinkedin.dto;

import java.util.List;

public class CompanyDTO {
	
	int id;
	String name;
	String password;
	String sector;
	String description;
	List<OfferorDTO> offerorList;
	
	public CompanyDTO() {}
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<OfferorDTO> getOfferorList() {
		return offerorList;
	}
	public void setOfferorList(List<OfferorDTO> offerorList) {
		this.offerorList = offerorList;
	}
}

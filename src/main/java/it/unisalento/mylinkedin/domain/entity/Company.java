package it.unisalento.mylinkedin.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Company {
	
	
	
public Company() {}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	String name;
	String password;
	String sector;
	String description;
	
	@OneToMany(mappedBy = "company", targetEntity = Offeror.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<Offeror> offerorList;

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

	public List<Offeror> getOfferorList() {
		return offerorList;
	}

	public void setOfferorList(List<Offeror> offerorList) {
		this.offerorList = offerorList;
	}
}

package it.unisalento.mylinkedin.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;

@Entity
public class Attribute {
	
	
	
public Attribute() {}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	@Id
	int id;
	@Column(unique = true)
	String name;
	String type;
	@Column(columnDefinition = "bit(1) default 1")
	boolean deletable;
	
	@OneToMany(mappedBy = "attribute", targetEntity = StructureHasAttribute.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<StructureHasAttribute> structureHasAttributeList;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<StructureHasAttribute> getStructureHasAttributeList() {
		return structureHasAttributeList;
	}

	public void setStructureHasAttributeList(List<StructureHasAttribute> structureHasAttributeList) {
		this.structureHasAttributeList = structureHasAttributeList;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
	
}

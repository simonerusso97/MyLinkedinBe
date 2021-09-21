package it.unisalento.mylinkedin.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;

@Entity
public class Structure {
	
	
	public Structure() {
	}
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	String name;
	String description;
	String userType;
	
	@OneToMany(mappedBy = "structure", targetEntity = Post.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<Post> postList;

	// TODO: CHIEDERE AIUTO
	@OneToMany(mappedBy = "structure", targetEntity = StructureHasAttribute.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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

	public List<Post> getPostList() {
		return postList;
	}

	public void setPostList(List<Post> postList) {
		this.postList = postList;
	}

	public List<StructureHasAttribute> getStructureHasAttributeList() {
		return structureHasAttributeList;
	}

	public void setStructureHasAttributeList(List<StructureHasAttribute> structureHasAttributeList) {
		this.structureHasAttributeList = structureHasAttributeList;
	}
}

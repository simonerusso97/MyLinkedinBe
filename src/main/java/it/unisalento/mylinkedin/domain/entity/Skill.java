package it.unisalento.mylinkedin.domain.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;

@Entity
public class Skill {
	
	

	public Skill() {
	}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	String name;
	String description;
	
	@OneToMany(mappedBy = "skill", targetEntity = PostRequireSkill.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<PostRequireSkill> postRequireSkillList;

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

	public List<PostRequireSkill> getPostRequireSkillList() {
		return postRequireSkillList;
	}

	public void setPostRequireSkillList(List<PostRequireSkill> postRequireSkillList) {
		this.postRequireSkillList = postRequireSkillList;
	}

}

package it.unisalento.mylinkedin.domain.relationship;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import it.unisalento.mylinkedin.domain.entity.Attribute;
import it.unisalento.mylinkedin.domain.entity.Structure;

@Entity
public class StructureHasAttribute {
	
	public StructureHasAttribute() {}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	@Column(columnDefinition = "bit(1) default 1")
	boolean deletable;
	
	@ManyToOne
	Structure structure;
	
	@ManyToOne
	Attribute attribute;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Structure getStructure() {
		return structure;
	}

	public void setStructure(Structure structure) {
		this.structure = structure;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}
	
	
}
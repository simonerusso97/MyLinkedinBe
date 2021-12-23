package it.unisalento.mylinkedin.domain.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;

@Entity
public class ToNotifyPost {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	int idPost;	
	int idUser;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdPost() {
		return idPost;
	}
	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	

	
}

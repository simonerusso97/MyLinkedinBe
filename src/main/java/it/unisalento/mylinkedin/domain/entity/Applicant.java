package it.unisalento.mylinkedin.domain.entity;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("applicant")
public class Applicant extends Regular{
	
	public Applicant() {
	}
	
}

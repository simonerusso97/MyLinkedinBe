package it.unisalento.mylinkedin.domain.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("admin")
public class Admin extends User{
	public Admin() {
	}
	
}

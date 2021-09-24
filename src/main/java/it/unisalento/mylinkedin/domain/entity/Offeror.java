package it.unisalento.mylinkedin.domain.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@Entity
@DiscriminatorValue("offeror")
public class Offeror extends Regular{
	
	
	public Offeror() {
	}
	String position;
	@Column(columnDefinition = "bit(1) default 0")
	boolean verified;
	
	@ManyToOne
	Company company;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	

}

package it.unisalento.mylinkedin.dto;


public class OfferorDTO extends RegularDTO{
	
	public OfferorDTO() {
		super.type = "offeror";
	}
	
	String position;
	boolean verified;
	CompanyDTO company;
	
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

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}
}

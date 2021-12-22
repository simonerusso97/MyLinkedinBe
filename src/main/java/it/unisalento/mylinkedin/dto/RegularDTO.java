package it.unisalento.mylinkedin.dto;

import java.util.List;

public class RegularDTO extends UserDTO{

	
	public RegularDTO() {
		super.type = "regular";
	}

	String address;
	boolean banned;
	boolean disabled;
	String token;
	//List<PostDTO> interestedPostList;
	String degree;	
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean isBanned() {
		return banned;
	}

	public void setBanned(boolean banned) {
		this.banned = banned;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	

}

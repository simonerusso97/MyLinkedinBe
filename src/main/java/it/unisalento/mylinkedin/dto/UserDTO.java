package it.unisalento.mylinkedin.dto;

import java.util.Date;
import java.util.List;

public class UserDTO {
	
	int id;
	String name;
	String surname;
	String email;
	Date birthDate;
	String password;
	String type;
	
	
	public UserDTO() {}
	
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

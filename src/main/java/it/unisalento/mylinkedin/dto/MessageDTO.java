package it.unisalento.mylinkedin.dto;

import java.util.Date;

public class MessageDTO {
	int id;
	Date date;
	UserDTO receivingUser;
	UserDTO sendingUser;
	String text;
	
	
	public MessageDTO() {}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public UserDTO getReceivingUser() {
		return receivingUser;
	}
	public void setReceivingUser(UserDTO receivingUser) {
		this.receivingUser = receivingUser;
	}
	public UserDTO getSendingUser() {
		return sendingUser;
	}
	public void setSendingUser(UserDTO sendingUser) {
		this.sendingUser = sendingUser;
	}



	public String getText() {
		return text;
	}



	public void setText(String text) {
		this.text = text;
	}
	
}

package it.unisalento.mylinkedin.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;


@Entity
public class Message{
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	Date date;
	String text;
	@ManyToOne
	User receivingUser;
	@ManyToOne()
	User sendingUser;
	
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public User getReceivingUser() {
		return receivingUser;
	}
	public void setReceivingUser(User receivingUser) {
		this.receivingUser = receivingUser;
	}
	public User getSendingUser() {
		return sendingUser;
	}
	public void setSendingUser(User sendingUser) {
		this.sendingUser = sendingUser;
	}
}

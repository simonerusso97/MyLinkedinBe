package it.unisalento.mylinkedin.domain.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;


@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public class User {
	
	
public User() {}
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	String name;
	String surname;
	String email;
	Date birthDate;
	String password;
	@OneToMany(mappedBy = "sendingUser", targetEntity = Message.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<Message> sentMessageList; 
	@OneToMany(mappedBy = "receivingUser", targetEntity = Message.class, cascade = CascadeType.ALL , fetch = FetchType.LAZY)
	List<Message> receivedMessageList; 
	
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
	public List<Message> getSentMessageList() {
		return sentMessageList;
	}
	public void setSentMessageList(List<Message> sentMessageList) {
		this.sentMessageList = sentMessageList;
	}
	public List<Message> getReceivedMessageList() {
		return receivedMessageList;
	}
	public void setReceivedMessageList(List<Message> receivedMessageList) {
		this.receivedMessageList = receivedMessageList;
	}
}

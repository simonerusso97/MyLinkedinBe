package it.unisalento.mylinkedin.dto;

import java.util.ArrayList;
import java.util.List;

public class ToNotifyPostDTO {
	int id;
	List<RegularDTO> users = new ArrayList<>();
	PostDTO post;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<RegularDTO> getUsers() {
		return users;
	}
	public void setUsers(List<RegularDTO> users) {
		this.users = users;
	}
	public PostDTO getPost() {
		return post;
	}
	public void setPost(PostDTO post) {
		this.post = post;
	}
	
	
	

}

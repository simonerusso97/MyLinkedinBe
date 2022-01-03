package it.unisalento.mylinkedin.dto;

public class ToNotifyPostDTO {
	int id;
	RegularDTO regularDTO;
	PostDTO postDTO;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RegularDTO getRegularDTO() {
		return regularDTO;
	}
	public void setRegularDTO(RegularDTO regularDTO) {
		this.regularDTO = regularDTO;
	}
	public PostDTO getPostDTO() {
		return postDTO;
	}
	public void setPostDTO(PostDTO postDTO) {
		this.postDTO = postDTO;
	}
	
	

}

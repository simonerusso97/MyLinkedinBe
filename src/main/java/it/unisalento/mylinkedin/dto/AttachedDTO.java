package it.unisalento.mylinkedin.dto;

import org.springframework.web.multipart.MultipartFile;

public class AttachedDTO {
	
	int id;
	String filename;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	
}

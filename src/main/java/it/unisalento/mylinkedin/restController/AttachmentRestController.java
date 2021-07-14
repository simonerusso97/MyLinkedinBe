package it.unisalento.mylinkedin.restController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.dto.AttachedDTO;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.iService.IAttachedService;
import it.unisalento.mylinkedin.iService.IPostService;


@RestController
@RequestMapping("/api/attachment")
public class AttachmentRestController {
	
	@Autowired
	IAttachedService attchedService;
	
	@Autowired
	IPostService postService;

	private static String UPLOADED_FOLDER = "/Users/simonerusso/Documents/GitHub/MyLinkedin/uploads/";
	
	@PostMapping("/uploadFile/{idPost}")
	public ResponseEntity<?> upload(@PathVariable("idPost") int idPost, @RequestAttribute("file") MultipartFile file) throws IOException, OperationFailedException {
		
		System.out.println("fileName:"+file.getOriginalFilename());
		Attached attached = new Attached();;

		attached.setName(saveFile(file));
		attached.setPost(postService.findById(idPost));
		attchedService.save(attached);
		
		return new ResponseEntity("OK", HttpStatus.OK);
	}
	
	
	/*@PostMapping("/uploadFileAndInfo")
	public ResponseEntity<?> uploadWithInfo(@ModelAttribute AttachedDTO attachment) throws IOException {
		
		saveFile(attachment.getFile());
		
		
		return new ResponseEntity("OK", HttpStatus.OK);
	}*/
	
	
	private String saveFile(MultipartFile file) throws IOException {
		byte[] bytes = file.getBytes();
		String filename = generateUID()+file.getOriginalFilename();
        Path path = Paths.get(UPLOADED_FOLDER + filename);
        Files.write(path, bytes);
        return filename;
	}
	
	private String generateUID () {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
}

package it.unisalento.mylinkedin.restController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.dto.AttachedDTO;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;
import it.unisalento.mylinkedin.iService.IAttachedService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.serviceImpl.AttachedService;

@RestController
@RequestMapping("/api/attachment")
public class AttachedRestController {
	
	@Autowired
	IAttachedService attchedService;
	
	@Autowired
	IPostService postService;

	private static String UPLOADED_FOLDER = "/Users/simonerusso/Documents/GitHub/MyLinkedinBe/uploads/";
	
	@PostMapping("/uploadFile/{idPost}/{type}")
	public ResponseEntity<?> upload(@PathVariable("idPost") int idPost, @PathVariable("type") String type, @RequestAttribute("file") MultipartFile file) throws IOException, OperationFailedException, PostNotFoundException {
		
		System.out.println("fileName:"+file.getOriginalFilename());
		Attached attached = new Attached();;

		attached.setName(saveFile(file));
		attached.setPost(postService.findById(idPost));
		attached.setType(type);
		attchedService.save(attached);
		
		return new ResponseEntity("OK", HttpStatus.OK);
	}
	

	@RequestMapping(value="/getAttached/{id}", method = RequestMethod.GET)
	public List<AttachedDTO> getAttached(@PathVariable("id") int id) throws IOException, OperationFailedException {
		 //TODO:controllare che funzioni
		String base64;
		List<Attached> attachedList = attchedService.findByIdPost(id);
		List<AttachedDTO> attachedDTOList = new ArrayList<>();
		AttachedDTO attachedDTO;
		for (Attached a : attachedList) {
			Attached attached = attchedService.findById(a.getId());
			base64 = DatatypeConverter.printBase64Binary(Files.readAllBytes(
					Paths.get(UPLOADED_FOLDER+attached.getName())));
			
			attachedDTO = new AttachedDTO();
			attachedDTO.setId(attached.getId());
			attachedDTO.setFilename(attached.getName());
			attachedDTO.setType(attached.getType());
			attachedDTO.setCode(base64);
			
			attachedDTOList.add(attachedDTO);
		}
		return attachedDTOList;
	}
	
	
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
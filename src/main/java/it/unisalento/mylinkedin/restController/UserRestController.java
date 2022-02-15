package it.unisalento.mylinkedin.restController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import it.unisalento.mylinkedin.domain.entity.Admin;
import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.domain.entity.Message;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.AttachedDTO;
import it.unisalento.mylinkedin.dto.JsonDocumentDTO;
import it.unisalento.mylinkedin.dto.MessageDTO;
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
import it.unisalento.mylinkedin.dto.StructureDTO;
import it.unisalento.mylinkedin.dto.UserDTO;
import it.unisalento.mylinkedin.exceptions.CompanyNotFound;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.ICompanyService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.IUserService;
import it.unisalento.mylinkedin.strategy.login.LoginAdminImpl;
import it.unisalento.mylinkedin.strategy.login.LoginApplicantImpl;
import it.unisalento.mylinkedin.strategy.login.LoginContext;
import it.unisalento.mylinkedin.strategy.login.LoginOfferorImpl;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@RestController
@RequestMapping("/user")
public class UserRestController {
	
	@Autowired
	IUserService userService;
	
	@Autowired
	ICompanyService companyService;

	@Autowired
	IPostService postService;
	
	
	
	@RequestMapping(value="/login/{email}/{pwd}",  method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDTO Login(@PathVariable("email") String email, @PathVariable("pwd") String pwd) throws UserNotFoundException, FileNotFoundException, IOException, ParseException{
		User user=new User();
		user=userService.findByEmailAndPassword(email.toLowerCase(), pwd);
		LoginContext loginContext = null;

		if(user.getClass() == Applicant.class) {
			loginContext = new LoginContext(new LoginApplicantImpl());
			
		}else if(user.getClass() == Offeror.class) {
			loginContext = new LoginContext(new LoginOfferorImpl());
			
		}else if(user.getClass() == Admin.class) {
			loginContext = new LoginContext(new LoginAdminImpl());
		}
		return loginContext.login(user, userService);
	}
	
	
	@RequestMapping(value="/findAllRegistrationRequest",  method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<RegularDTO> findAllRegistrationRequest(){
		List<Regular> regularList = userService.findRegularByDisabled(true);
		List<RegularDTO> list = new ArrayList<RegularDTO>();
		
		for (Regular regular : regularList) {

			RegularDTO regularDTO = new RegularDTO();
			regularDTO.setId(regular.getId());
			regularDTO.setBirthDate(regular.getBirthDate());
			regularDTO.setEmail(regular.getEmail());
			regularDTO.setName(regular.getName());
			regularDTO.setSurname(regular.getSurname());
			regularDTO.setDisabled(regular.isDisabled());
			regularDTO.setBanned(regular.isBanned());
			regularDTO.setDegree(regular.getDegree());
			regularDTO.setAddress(regular.getAddress());
			
			list.add(regularDTO);
		}
		
		
		return list;
	}
	

	@RequestMapping(value="/acceptUser",  method=RequestMethod.PATCH, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegularDTO> acceptUser(@RequestBody @Valid RegularDTO regularDTO) throws UserNotFoundException{

		Regular regular = userService.findById(regularDTO.getId());
		regular.setDisabled(regularDTO.isDisabled());
		userService.save(regular);
		return new ResponseEntity<RegularDTO>(HttpStatus.OK);

	}

	@RequestMapping(value="/getAllDisabledUser", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<RegularDTO> getAllDisabledUser(){
		List<Regular> regularList = new ArrayList<>();
		regularList = userService.findRegularByDisabled(false);
		List<RegularDTO> regularDTOList = new ArrayList<>();
		
		for (Regular regular : regularList) {
			RegularDTO regularDTO = new RegularDTO();
			regularDTO.setAddress(regular.getAddress());
			regularDTO.setBanned(regular.isBanned());
			regularDTO.setBirthDate(regular.getBirthDate());
			regularDTO.setDegree(regular.getDegree());
			regularDTO.setDisabled(regular.isDisabled());
			regularDTO.setEmail(regular.getEmail());
			regularDTO.setId(regular.getId());
			regularDTO.setName(regular.getName());
			regularDTO.setSurname(regular.getSurname());
			
			regularDTOList.add(regularDTO);
		}
		
		return regularDTOList;
	}
	
	@RequestMapping(value = "/banUnban", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<RegularDTO> banUnban(@RequestBody @Valid RegularDTO regularDTO) throws UserNotFoundException{
			Regular regular = userService.findById(regularDTO.getId());
			regular.setBanned(regularDTO.isBanned());
			userService.save(regular);
			return new ResponseEntity<RegularDTO>(HttpStatus.OK);

	}
	
	@RequestMapping(value="/applicatSignUp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ApplicantDTO> registrationApplicant(@RequestBody @Valid ApplicantDTO applicantDTO){
			Applicant applicant = new Applicant();

			userService.findByEmail(applicantDTO.getEmail().toLowerCase());

			applicant.setAddress(applicantDTO.getAddress());
			applicant.setBanned(applicantDTO.isBanned());
			applicant.setBirthDate(applicantDTO.getBirthDate());
			applicant.setDegree(applicantDTO.getDegree());
			applicant.setEmail(applicantDTO.getEmail().toLowerCase());
			applicant.setName(applicantDTO.getName());
			applicant.setPassword(applicantDTO.getPassword());
			applicant.setSurname(applicantDTO.getSurname());
			applicant.setDisabled(true);
			
			userService.save(applicant);
			return new ResponseEntity<ApplicantDTO>(HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/offerorSignUp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<OfferorDTO> registrationOfferor(@RequestBody @Valid OfferorDTO offerorDTO) throws CompanyNotFound{
		
			Offeror offeror = new Offeror();
			userService.findByEmail(offerorDTO.getEmail().toLowerCase());
			
			offeror.setAddress(offerorDTO.getAddress());
			offeror.setBanned(offerorDTO.isBanned());
			offeror.setBirthDate(offerorDTO.getBirthDate());
			offeror.setDegree(offerorDTO.getDegree());
			offeror.setEmail(offerorDTO.getEmail().toLowerCase());
			offeror.setName(offerorDTO.getName());
			offeror.setPassword(offerorDTO.getPassword());
			offeror.setSurname(offerorDTO.getSurname());
			offeror.setDisabled(true);
			offeror.setPosition(offerorDTO.getPosition());
			offeror.setVerified(offerorDTO.isVerified());
			
			offeror.setCompany(companyService.findById(offerorDTO.getCompany().getId()));
			userService.save(offeror);
			
			return new ResponseEntity<OfferorDTO>(HttpStatus.CREATED);
			
		}
	
	
	@RequestMapping(value="/findOfferorNotVerifed/{idCompany}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<OfferorDTO> findOfferorNotVerifed(@PathVariable("idCompany") int idCompany){
		
		List<Offeror> offerorList = userService.findByCompanyIdAndVerified(idCompany, false);
		List<OfferorDTO> offerorDTOList = new ArrayList<>();

		for (Offeror offeror : offerorList) {
			OfferorDTO offerorDTO = new OfferorDTO();
			offerorDTO.setId(offeror.getId());
			offerorDTO.setName(offeror.getName());
			offerorDTO.setSurname(offeror.getSurname());
			offerorDTO.setEmail(offeror.getEmail());
			offerorDTO.setAddress(offeror.getAddress());
			offerorDTO.setPosition(offeror.getPosition());
			
			offerorDTOList.add(offerorDTO);
			}
		return offerorDTOList;
	}
	
	@RequestMapping(value = "/acceptOfferor", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<OfferorDTO> acceptOfferor(@RequestBody @Valid OfferorDTO offerorDTO) throws UserNotFoundException{
			Offeror offeror = (Offeror) userService.findById(offerorDTO.getId());
			offeror.setVerified(true);
			userService.save(offeror);
			return new ResponseEntity<OfferorDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateInterestedList/{userId}",method=RequestMethod.PATCH, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegularDTO> savePost (@PathVariable("userId") int userId, @RequestBody @Valid List<PostDTO> newInterestedList) throws PostNotFoundException, UserNotFoundException{
		List<PostDTO> tempList = new ArrayList<>(); //tempList contiene i nuovi post che devono essere aggiunti
		List<RegularInterestedInPost> updatedList = new ArrayList<>();
		List<RegularInterestedInPost> toRemoveList = new ArrayList<>();//toRemoveList contiene i post che non sono più presenti tra quelli salvati
		List<Post> toNotifyPost = new ArrayList<>();// toNotifyPost contiene i post che devono essere notificati a fine giornate

		RegularInterestedInPost regularInterestedInPost;
		Post post;
		boolean found = false;
		
		Regular regular = userService.findById(userId);
		tempList.addAll(newInterestedList);
		
		
		List<RegularInterestedInPost> oInterestedList = userService.findInterestedPostByUserId(userId);
		
		//Cerco tutti i post che erano presenti nella lista prima della chiamata e che sono ancora presenti
		//nella lista dopo la chiamata e li salvo in updatedList 
		for (RegularInterestedInPost oRIIP : oInterestedList) {
			for (PostDTO newPostDTO : newInterestedList) {
				if(oRIIP.getPost().getId() == newPostDTO.getId()) {
					updatedList.add(oRIIP);
					tempList.remove(newPostDTO);
					found = true;
					break;
				}
			}
			if(!found) {
				toRemoveList.add(oRIIP);
			}
		}
		
		//Ora aggiungo in updatedList i post che non erano ancora prensti 
		for (PostDTO postDTO : tempList) {
			post = postService.findById(postDTO.getId());
			regularInterestedInPost = new RegularInterestedInPost();
			regularInterestedInPost.setPost(post);
			regularInterestedInPost.setRegular(regular);
			regularInterestedInPost.setNotified(false);
			updatedList.add(regularInterestedInPost);
			toNotifyPost.add(post);
			}
		
		userService.updateInterestedList(updatedList);
		
		//salvo i nuovi post per inviare una notifica a fine giornata
		
		//Rimuovo i post non più presenti
		userService.removeInterest(toRemoveList);
		
		return new ResponseEntity<RegularDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/findAllInterestedPost/{idUser}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<PostDTO> findAllInterestedPost(@PathVariable("idUser") int idUser) throws UserNotFoundException, IOException, ParseException{
		List<RegularInterestedInPost> riipList = postService.findInterestedPost(idUser);
		List<PostDTO> postDTOList = new ArrayList<>();
		PostDTO postDTO;
		for (RegularInterestedInPost riip : riipList) {
			postDTO = new PostDTO();
			postDTO.setHide(riip.getPost().isHide());
			postDTO.setId(riip.getPost().getId());
			postDTO.setName(riip.getPost().getName());
			postDTO.setPubblicationDate(riip.getPost().getPubblicationDate());
			
			List<SkillDTO> skillDTOList = new ArrayList<>();
			
			List<PostRequireSkill> postRequireSkillList = riip.getPost().getPostRequireSkillList();
			for (PostRequireSkill postrequireSkill : postRequireSkillList) {
				SkillDTO skillDTO = new SkillDTO();
				skillDTO.setDescription(postrequireSkill.getSkill().getDescription());
				skillDTO.setId(postrequireSkill.getSkill().getId());
				skillDTO.setName(postrequireSkill.getSkill().getName());
				
				skillDTOList.add(skillDTO);
			}
			
			postDTO.setSkillList(skillDTOList);
			
			RegularDTO regularDTO = new RegularDTO();
			Regular regular = (Regular) userService.findById(riip.getPost().getCreatedBy().getId());
			regularDTO.setId(regular.getId());
			regularDTO.setName(regular.getName());
			regularDTO.setSurname(regular.getSurname());
			regularDTO.setEmail(regular.getEmail());		
			
			postDTO.setCreatedBy(regularDTO);
			List<Attached> attachedList = riip.getPost().getAttachedList();
			List<AttachedDTO> attachedDTOList = new ArrayList<>(); 
			AttachedDTO attachedDTO;
			
			for (Attached attached : attachedList) {
				attachedDTO = new AttachedDTO();
				attachedDTO.setId(attached.getId());
				attachedDTO.setFilename(attached.getName());
				attachedDTO.setType(attached.getType());
				String code = encodeFileToBase64(new File("uploads/" + attached.getName()));
				attachedDTO.setCode(code);
				attachedDTOList.add(attachedDTO);
			}
			postDTO.setAttachedList(attachedDTOList);
			
			StructureDTO structureDTO = new StructureDTO();
			structureDTO.setDescription(riip.getPost().getStructure().getDescription());
			structureDTO.setId(riip.getPost().getStructure().getId());
			structureDTO.setName(structureDTO.getName());
			postDTO.setStructure(structureDTO);
			
			JSONParser parser = new JSONParser();
			Reader reader = new FileReader(riip.getPost().getJsonDocument().getName());
			JSONObject jsonObject = (JSONObject) parser.parse(reader);
			
			Set<String> keys = jsonObject.keySet(); 
			
			List<JsonDocumentDTO> jsonDocumentDTOList = new ArrayList<>();
			for (String key : keys) {
				JsonDocumentDTO jsonDocumentDTO = new JsonDocumentDTO();
				jsonDocumentDTO.setNameAttribute(key);
				jsonDocumentDTO.setValue((String) jsonObject.get(key));

				jsonDocumentDTOList.add(jsonDocumentDTO);
			}
			
			
			postDTO.setJsonDocument(jsonDocumentDTOList);
			
			postDTOList.add(postDTO);
		}
		return postDTOList;
	}
	
	@RequestMapping(value="/getAllMessage/{idUser}",  method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<MessageDTO> getAllMessage(@PathVariable("idUser") int idUser) throws UserNotFoundException{
		List<Message> messageList = userService.findMessageByUserId(idUser);
		List<MessageDTO> messageDTOList = new ArrayList<>();
		User user;
		UserDTO userDTO;
		MessageDTO messageDTO;
		for (Message message : messageList) {
			messageDTO = new MessageDTO();
			messageDTO.setId(message.getId());
			messageDTO.setDate(message.getDate());
			messageDTO.setText(message.getText());
			userDTO = new UserDTO();
			user = userService.findUserById(message.getSendingUser().getId());
			userDTO.setId(user.getId());
			userDTO.setBirthDate(user.getBirthDate());
			userDTO.setEmail(user.getEmail());
			userDTO.setName(user.getName());
			userDTO.setSurname(user.getSurname());
			
			if(user.getClass() == Applicant.class) {
				userDTO.setType("Applicant");
			}
			if(user.getClass() == Offeror.class) {
				userDTO.setType("Offeror");
			}
			if(user.getClass() == Admin.class) {
				userDTO.setType("Admin");
			}
			
			messageDTO.setSendingUser(userDTO);
			
			userDTO = new UserDTO();
			user = userService.findUserById(message.getReceivingUser().getId());
			userDTO.setId(user.getId());
			userDTO.setBirthDate(user.getBirthDate());
			userDTO.setEmail(user.getEmail());
			userDTO.setName(user.getName());
			userDTO.setSurname(user.getSurname());
			
			if(user.getClass() == Applicant.class) {
				userDTO.setType("Applicant");
			}
			if(user.getClass() == Offeror.class) {
				userDTO.setType("Offeror");
			}
			if(user.getClass() == Admin.class) {
				userDTO.setType("Admin");
			}
			
			
			messageDTO.setReceivingUser(userDTO);
			
			messageDTOList.add(messageDTO);
		}
		
		return messageDTOList;
	}
	
	@RequestMapping(value="/getAllUser", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<UserDTO> getAllUser(){
		List<User> userList = new ArrayList<>();
		userList = userService.findAllUser();
		List<UserDTO> userDTOList = new ArrayList<>();
		
		for (User user : userList) {
			UserDTO userDTO = new RegularDTO();
			
			userDTO.setBirthDate(user.getBirthDate());
			userDTO.setEmail(user.getEmail());
			userDTO.setId(user.getId());
			userDTO.setName(user.getName());
			userDTO.setSurname(user.getSurname());
			
			if(user.getClass() == Applicant.class) {
				userDTO.setType("Applicant");
			}
			if(user.getClass() == Offeror.class) {
				userDTO.setType("Offeror");
			}
			if(user.getClass() == Admin.class) {
				userDTO.setType("Admin");
			}
			
			userDTOList.add(userDTO);
		}
		
		return userDTOList;
	}
	
	@RequestMapping(value="/getAllRegular", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<UserDTO> getAllRegular(){
		List<User> userList = new ArrayList<>();
		userList = userService.findAllUser();
		List<UserDTO> userDTOList = new ArrayList<>();
		
		for (User user : userList) {
			UserDTO userDTO = new RegularDTO();
			if(user.getClass() != Admin.class) {
				userDTO.setType("Admin");
			
				userDTO.setBirthDate(user.getBirthDate());
				userDTO.setEmail(user.getEmail());
				userDTO.setId(user.getId());
				userDTO.setName(user.getName());
				userDTO.setSurname(user.getSurname());
				
				if(user.getClass() == Applicant.class) {
					userDTO.setType("Applicant");
				}
				if(user.getClass() == Offeror.class) {
					userDTO.setType("Offeror");
				}
				
				
				userDTOList.add(userDTO);
			}
		}
		
		return userDTOList;
	}
	
	@RequestMapping(value="/sendMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<MessageDTO> sendMessage(@RequestBody @Valid MessageDTO messageDTO) throws UserNotFoundException{
		Message message = new Message();
		message.setDate(messageDTO.getDate());
		User user = userService.findUserById(messageDTO.getReceivingUser().getId());
		message.setReceivingUser(user);
		user = userService.findUserById(messageDTO.getSendingUser().getId());
		message.setSendingUser(user);
		message.setText(messageDTO.getText());
		userService.saveMessage(message);
		return new ResponseEntity<MessageDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveToken/{idUser}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<String> saveToken(@PathVariable("idUser") int idUser, @RequestBody @Valid String token) throws UserNotFoundException{
		Regular regular = userService.findById(idUser);
		regular.setToken(token);
		userService.save(regular);
		return new ResponseEntity<String>(HttpStatus.OK);

	}
	@RequestMapping(value="/sendNotification/{idUser}/{name}/{surname}", method = RequestMethod.GET)
	private ResponseEntity<String> sendNotification(@PathVariable("idUser") int idUser, @PathVariable("name") String name, @PathVariable("surname") String surname) throws FirebaseMessagingException, UserNotFoundException, IOException{
		Regular user = (Regular) userService.findUserById(idUser);
		
		Notification.Builder builder = Notification.builder();
		MulticastMessage notMess = MulticastMessage.builder()
				.setNotification(builder.setTitle("Qualcuno è interessato al tuo post")
						.setBody(name+ ' '+ surname)
						.build())
				.putData("notification_foreground", "true")
		        .addToken(user.getToken())
		        .build();
		System.out.println("Sending notification...");
		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(notMess);
		System.out.println(response.getSuccessCount()+ " messages were sent successfully");
		return new ResponseEntity<String>(HttpStatus.OK);
	}
	
	private static String encodeFileToBase64(File file) {
	    try {
	        byte[] fileContent = Files.readAllBytes(file.toPath());
	        return Base64.getEncoder().encodeToString(fileContent);
	    } catch (IOException e) {
	        throw new IllegalStateException("could not read file " + file, e);
	    }
	}
}

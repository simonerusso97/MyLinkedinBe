package it.unisalento.mylinkedin.restController;

import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.mylinkedin.domain.entity.Admin;
import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.domain.entity.Message;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.dto.AdminDTO;
import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.dto.MessageDTO;
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.UserDTO;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.UserAlreadyExist;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.ICompanyService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.IUserService;
import it.unisalento.mylinkedin.serviceImpl.PostServiceImpl;
import it.unisalento.mylinkedin.strategy.login.LoginAdminImpl;
import it.unisalento.mylinkedin.strategy.login.LoginApplicantImpl;
import it.unisalento.mylinkedin.strategy.login.LoginContext;
import it.unisalento.mylinkedin.strategy.login.LoginOfferorImpl;


@RestController
@RequestMapping("/user")
public class UserRestController {
	@Autowired
	IUserService userService;
	
	@Autowired
	ICompanyService companyService;
	
	@Autowired
	IPostService postService;

	
	@RequestMapping(value= "/getAllDisabledRegularUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE )
	public List<RegularDTO> getAllDisabledRegularUser() {
		
		
		List<Regular> regularList = userService.getAllDisabledRegularUser();
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
	
	@RequestMapping(value = "/acceptRegistrationRequest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<RegularDTO> acceptRegistrationRequest(@RequestBody @Valid RegularDTO regularDTO) throws OperationFailedException, UserNotFoundException{
		Regular regular = userService.findById(regularDTO.getId());
		regular.setDisabled(regularDTO.isDisabled());
		userService.updateRegularUser(regular);
		return new ResponseEntity<RegularDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value = "/banUnbanRegularUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<RegularDTO> banUnbanRegularUser(@RequestBody @Valid RegularDTO regularDTO) throws OperationFailedException, UserNotFoundException{
		Regular regular = userService.findById(regularDTO.getId());
		regular.setBanned(regularDTO.isBanned());
		userService.updateRegularUser(regular);
		return new ResponseEntity<RegularDTO>(HttpStatus.OK);

	}
	
	@RequestMapping(value="/registrationOfferor", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<OfferorDTO> registrationOfferor(@RequestBody @Valid OfferorDTO offerorDTO) throws OperationFailedException, UserAlreadyExist{
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
		offeror.setDisabled(offerorDTO.isDisabled());
		offeror.setPosition(offerorDTO.getPosition());
		offeror.setVerified(offerorDTO.isVerified());
		
		offeror.setCompany(companyService.findById(offerorDTO.getCompany().getId()));
		userService.saveOfferor(offeror);
		return new ResponseEntity<OfferorDTO>(HttpStatus.OK);
		
		
	}
	@RequestMapping(value="/registrationApplicant", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<ApplicantDTO> registrationApplicant(@RequestBody @Valid ApplicantDTO applicantDTO) throws OperationFailedException, UserAlreadyExist{
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
		applicant.setDisabled(applicantDTO.isDisabled());
		
		userService.saveApplicant(applicant);
		return new ResponseEntity<ApplicantDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/login/{email}/{pwd}",  method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public UserDTO Login(@PathVariable("email") String email, @PathVariable("pwd") String pwd) throws UserNotFoundException, FileNotFoundException, IOException, ParseException  {
		User user=new User();
		user=userService.findByEmailAndPassword(email, pwd);
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
	
	@RequestMapping(value="/getAllRegularEnabled", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<RegularDTO> getAllRegular(){
		List<Regular> regularList = new ArrayList<>();
		regularList = userService.findAllRegularEnabled();
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
	
	@RequestMapping(value="/updateInterestedList/{id}",method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegularDTO> savePost (@PathVariable("id") int id, @RequestBody @Valid RegularDTO regularDTO) throws OperationFailedException, UserNotFoundException{
		Regular regular = userService.findById(regularDTO.getId());
		Post post = postService.findById(id);
		RegularInterestedInPost regularInterestedInPost = new RegularInterestedInPost();
		regularInterestedInPost.setPost(post);
		regularInterestedInPost.setRegular(regular);
		regular.getRegularInterestedInPostList().add(regularInterestedInPost);
		userService.save(regular);
		
		return new ResponseEntity<RegularDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/unsavePost/{id}", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegularDTO> unsavePost (@PathVariable("id") int id, @RequestBody @Valid RegularDTO regularDTO) throws OperationFailedException, UserNotFoundException{
		Regular regular=userService.findById(regularDTO.getId());

		RegularInterestedInPost item = null;
        List<RegularInterestedInPost> regularInterestedInPostList=regular.getRegularInterestedInPostList();
        for (RegularInterestedInPost regularInterestedInPost : regularInterestedInPostList) {
			if(regularInterestedInPost.getPost().getId()==id) {
				item = regularInterestedInPost;
				break;
			}
		}
        if(item != null) {
        	regularInterestedInPostList.remove(item);
        	regular.setRegularInterestedInPostList(regularInterestedInPostList);
        	userService.save(regular);
        }
        else {
        	throw new OperationFailedException();
        }
        return new ResponseEntity<RegularDTO>(HttpStatus.OK);

    }
	
	@RequestMapping(value="/findAll", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> findAll (){
		List<User> userList = userService.findAll();
		List<UserDTO> userDTOList = new ArrayList<>();
		for (User user : userList) {
			UserDTO userDTO= null;
			if(user.getClass() == Applicant.class) {
				userDTO = new ApplicantDTO();
				userDTO.setId(user.getId());
				userDTO.setName(user.getName());
				userDTO.setSurname(user.getSurname());
				userDTO.setEmail(user.getEmail());
			}
			if(user.getClass() == Offeror.class) {
				userDTO = new OfferorDTO();
				userDTO.setId(user.getId());
				userDTO.setName(user.getName());
				userDTO.setSurname(user.getSurname());
				userDTO.setEmail(user.getEmail());			
			}
			if(user.getClass() == Admin.class) {
				userDTO = new AdminDTO();
				userDTO.setId(user.getId());
				userDTO.setName(user.getName());
				userDTO.setSurname(user.getSurname());
				userDTO.setEmail(user.getEmail());			
			}
			userDTOList.add(userDTO);

		}
		
		return userDTOList;
	}
	
	
	@RequestMapping(value="/sendMessage", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody @Valid MessageDTO messageDTO) throws UserNotFoundException, OperationFailedException{
		Message message = new Message();
		message.setText(messageDTO.getText());
		message.setSendingUser(userService.findUserById(messageDTO.getSendingUser().getId()));
		message.setReceivingUser(userService.findUserById(messageDTO.getReceivingUser().getId()));
		message.setDate(messageDTO.getDate());
		userService.saveMessage(message);
		
		return new ResponseEntity<MessageDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/findMessage/{idUserChat}", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDTO> findMessage(@PathVariable("idUserChat") int idUserChat, @RequestBody @Valid UserDTO userDTO) throws UserNotFoundException, OperationFailedException{
		List<Message> messageList = userService.findMessages(userDTO.getId(), idUserChat);


		
		List<MessageDTO> messageDTOList = new ArrayList<>();
		for (Message message : messageList) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setText(message.getText());
			UserDTO userDTO1 = new UserDTO();
			userDTO1.setId(message.getSendingUser().getId());
			messageDTO.setSendingUser(userDTO1);
			userDTO1 = new UserDTO();
			userDTO1.setId(message.getReceivingUser().getId());
			messageDTO.setReceivingUser(userDTO1);
			messageDTO.setDate(message.getDate());
			
			messageDTOList.add(messageDTO);
		}
		return messageDTOList;
	}
	
	@RequestMapping(value="/findMessage", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDTO> findMessage(@RequestBody @Valid UserDTO userDTO) throws UserNotFoundException, OperationFailedException{
		List<Message> messageList = userService.findAllMessages(userDTO.getId());


		
		List<MessageDTO> messageDTOList = new ArrayList<>();
		for (Message message : messageList) {
			MessageDTO messageDTO = new MessageDTO();
			messageDTO.setText(message.getText());
			UserDTO userDTO1 = new UserDTO();
			userDTO1.setId(message.getSendingUser().getId());
			messageDTO.setSendingUser(userDTO1);
			userDTO1 = new UserDTO();
			userDTO1.setId(message.getReceivingUser().getId());
			messageDTO.setReceivingUser(userDTO1);
			messageDTO.setDate(message.getDate());
			
			messageDTOList.add(messageDTO);
		}
		return messageDTOList;
	}
	
	@RequestMapping(value="/findAllNotVerifiedUser/{idCompany}", method=RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public  List<OfferorDTO> findAllNotVerifiedUser(@PathVariable("idCompany") int idCompany) throws UserNotFoundException, OperationFailedException{
		List<Offeror> offerorList = userService.findByIdCompanyAndVerified(idCompany, false);
		List<OfferorDTO> offerorDTOList = new ArrayList<>();

		for (Offeror offeror : offerorList) {
			OfferorDTO offerorDTO = new OfferorDTO();
			offerorDTO.setName(offeror.getName());
			offerorDTO.setSurname(offeror.getSurname());
			offerorDTO.setEmail(offeror.getEmail());
			offerorDTO.setAddress(offeror.getAddress());
			offerorDTO.setPosition(offeror.getPosition());
			
			offerorDTOList.add(offerorDTO);
			}
		return offerorDTOList;
	}
	
	@RequestMapping(value="/verifyUser/{id}", method=RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<OfferorDTO> verifyUser(@PathVariable("id") int id) throws UserNotFoundException, OperationFailedException{
		Offeror offeror = (Offeror) userService.findById(id);
		offeror.setVerified(true);
		userService.saveOfferor(offeror);
		return new ResponseEntity<OfferorDTO>(HttpStatus.OK);
		
	}
	
	
}

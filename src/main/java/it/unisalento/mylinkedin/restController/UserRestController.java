package it.unisalento.mylinkedin.restController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.mylinkedin.domain.entity.Admin;
import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
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


import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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

	@RequestMapping(value="/getAllUser", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<RegularDTO> getAllRegular(){
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
			applicant.setDisabled(applicantDTO.isDisabled());
			
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
			offeror.setDisabled(offerorDTO.isDisabled());
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
    public ResponseEntity<RegularDTO> savePost (@PathVariable("userId") int userId, @RequestBody @Valid List<PostDTO> postList) throws PostNotFoundException, UserNotFoundException{
		List<PostDTO> tempList = new ArrayList<>();
		List<RegularInterestedInPost> updatedList = new ArrayList<>();
		List<RegularInterestedInPost> toRemoveList = new ArrayList<>();
		RegularInterestedInPost regularInterestedInPost;
		Post post;
		boolean found = false;
		
		Regular regular = userService.findById(userId);
		tempList.addAll(postList);
		
		
		List<RegularInterestedInPost> regularInterestedInPostList = userService.findInterestedPostByUserId(userId);
		for (RegularInterestedInPost RIIP : regularInterestedInPostList) {
			for (PostDTO postDTO : postList) {
				if(RIIP.getPost().getId() == postDTO.getId()) {
					updatedList.add(RIIP);
					tempList.remove(postDTO);
					found = true;
					break;
				}
			}
			if(!found) {
				toRemoveList.add(RIIP);
			}
		}
		
		for (PostDTO postDTO : tempList) {
			post = postService.findById(postDTO.getId());
			regularInterestedInPost = new RegularInterestedInPost();
			regularInterestedInPost.setPost(post);
			regularInterestedInPost.setRegular(regular);
			updatedList.add(regularInterestedInPost);
			}
		
		userService.updateInterestedList(updatedList);
		userService.removeInterest(toRemoveList);
		
		return new ResponseEntity<RegularDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/findAllInterestedPost/{idUser}", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<PostDTO> findAllInterestedPost(@PathVariable("idUser") int idUser) throws UserNotFoundException{
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
			
			postDTOList.add(postDTO);
		}
		return postDTOList;
	}
	
	
}

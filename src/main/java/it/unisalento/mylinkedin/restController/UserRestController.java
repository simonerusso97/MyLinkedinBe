package it.unisalento.mylinkedin.restController;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.mylinkedin.domain.entity.Admin;
import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.UserDTO;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
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
	
	@RequestMapping(value="/findAllRegistrationRequest",  method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<RegularDTO> findAllRegistrationRequest(){
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

	@RequestMapping(value="/acceptUser",  method=RequestMethod.PATCH, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RegularDTO> acceptUser(@RequestBody @Valid RegularDTO regularDTO){
		try {
			Regular regular = userService.findById(regularDTO.getId());
			regular.setDisabled(regularDTO.isDisabled());
			userService.updateRegularUser(regular);
		}catch (Exception e) {
			if(e.getClass() == OperationFailedException.class) {
				return new ResponseEntity<RegularDTO>(HttpStatus.NO_CONTENT);

			}
			else if(e.getClass() == UserNotFoundException.class) {
				return new ResponseEntity<RegularDTO>(HttpStatus.NOT_FOUND);

			}
		}
		return new ResponseEntity<RegularDTO>(HttpStatus.OK);

	}

	@RequestMapping(value="/getAllUser", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@RequestMapping(value = "/banUnban", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<RegularDTO> banUnbanRegularUser(@RequestBody @Valid RegularDTO regularDTO) throws OperationFailedException, UserNotFoundException{
		try {
			Regular regular = userService.findById(regularDTO.getId());
			regular.setBanned(regularDTO.isBanned());
			userService.updateRegularUser(regular);
			}catch (Exception e) {
				if(e.getClass() == OperationFailedException.class) {
					return new ResponseEntity<RegularDTO>(HttpStatus.NO_CONTENT);
				}
				else if(e.getClass() == UserNotFoundException.class) {
					return new ResponseEntity<RegularDTO>(HttpStatus.NOT_FOUND);

				}
			}
		return new ResponseEntity<RegularDTO>(HttpStatus.OK);

	}
	
}

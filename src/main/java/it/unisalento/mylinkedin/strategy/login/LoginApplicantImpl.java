package it.unisalento.mylinkedin.strategy.login;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Message;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;
import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.AttributeDTO;
import it.unisalento.mylinkedin.dto.JsonDocumentDTO;
import it.unisalento.mylinkedin.dto.MessageDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
import it.unisalento.mylinkedin.dto.StructureDTO;
import it.unisalento.mylinkedin.dto.UserDTO;
import it.unisalento.mylinkedin.iService.IUserService;

public class LoginApplicantImpl implements LoginStrategy{

	@Override
	public UserDTO login(User user, IUserService userService) throws FileNotFoundException, IOException, ParseException {
		ApplicantDTO applicantDTO = new ApplicantDTO();
		Applicant applicant = (Applicant) user;
		applicantDTO.setId(applicant.getId());
		applicantDTO.setName(applicant.getName());
		applicantDTO.setSurname(applicant.getSurname());
		applicantDTO.setEmail(applicant.getEmail());
		applicantDTO.setBirthDate(applicant.getBirthDate());
		applicantDTO.setPassword(applicant.getPassword());
		applicantDTO.setAddress(applicant.getAddress());
		applicantDTO.setBanned(applicant.isBanned());
		applicantDTO.setDisabled(applicant.isDisabled());
		applicantDTO.setDegree(applicant.getDegree());
		
       return applicantDTO;
	}
	
	

}

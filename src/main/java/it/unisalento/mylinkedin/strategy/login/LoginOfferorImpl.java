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

import it.unisalento.mylinkedin.domain.entity.Message;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.User;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;
import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.AttributeDTO;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.dto.JsonDocumentDTO;
import it.unisalento.mylinkedin.dto.MessageDTO;
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
import it.unisalento.mylinkedin.dto.StructureDTO;
import it.unisalento.mylinkedin.dto.UserDTO;
import it.unisalento.mylinkedin.iService.IUserService;

public class LoginOfferorImpl implements LoginStrategy{

	@Override
	public UserDTO login(User user, IUserService userService) throws FileNotFoundException, IOException, ParseException {
		OfferorDTO offerorDTO = new OfferorDTO();
		Offeror offeror = (Offeror) user;
		offerorDTO.setId(offeror.getId());
		offerorDTO.setName(offeror.getName());
		offerorDTO.setSurname(offeror.getSurname());
		offerorDTO.setEmail(offeror.getEmail());
		offerorDTO.setBirthDate(offeror.getBirthDate());
		offerorDTO.setPassword(offeror.getPassword());
		offerorDTO.setAddress(offeror.getAddress());
		offerorDTO.setBanned(offeror.isBanned());
		offerorDTO.setDisabled(offeror.isDisabled());
		offerorDTO.setDegree(offeror.getDegree());
		offerorDTO.setPosition(offeror.getPosition());
		offerorDTO.setVerified(offeror.isVerified());
				
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(offeror.getCompany().getId());
		companyDTO.setName(offeror.getCompany().getName());
		companyDTO.setSector(offeror.getCompany().getSector());
		companyDTO.setDescription(offeror.getCompany().getDescription());
		
		offerorDTO.setCompany(companyDTO);
		
		return offerorDTO;
	}

}

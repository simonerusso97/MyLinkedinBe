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
		
		List<Message> messageList = userService.findAllMessages(user.getId());
        List<MessageDTO> messageDTOList = new ArrayList<>();
        for (Message message : messageList) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setId(message.getId());
            messageDTO.setDate(message.getDate());
            messageDTO.setText(message.getText());
            UserDTO userDTO = new UserDTO();
            userDTO.setId(message.getReceivingUser().getId());
            userDTO.setName(message.getReceivingUser().getName());
            userDTO.setSurname(message.getReceivingUser().getSurname());
            userDTO.setEmail(message.getReceivingUser().getEmail());
            
            messageDTO.setReceivingUser(userDTO);
            userDTO = new UserDTO();
            userDTO.setId(message.getSendingUser().getId());
            userDTO.setName(message.getSendingUser().getName());
            userDTO.setSurname(message.getSendingUser().getSurname());
            userDTO.setEmail(message.getSendingUser().getEmail());
            
            messageDTO.setSendingUser(userDTO);

            messageDTOList.add(messageDTO);
        }

        offerorDTO.setMessageList(messageDTOList);		
		
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(offeror.getCompany().getId());
		companyDTO.setName(offeror.getCompany().getName());
		companyDTO.setSector(offeror.getCompany().getSector());
		companyDTO.setDescription(offeror.getCompany().getDescription());
		
		offerorDTO.setCompany(companyDTO);
		
		List<PostDTO> intrestedPostList = new ArrayList();
		
		for (RegularInterestedInPost regularInterestedInPost: offeror.getRegularInterestedInPostList()) {
			Post post = regularInterestedInPost.getPost();
			PostDTO postDTO = new PostDTO();
			postDTO.setId(post.getId());
			postDTO.setHide(post.isHide());
			postDTO.setPubblicationDate(post.getPubblicationDate());
			postDTO.setName(post.getName());
			StructureDTO structureDTO = new StructureDTO();
			structureDTO.setId(post.getStructure().getId());
			structureDTO.setName(post.getStructure().getName());
			structureDTO.setDescription(post.getStructure().getDescription());
			structureDTO.setUserType(post.getStructure().getUserType());
			List<AttributeDTO> attributeDTOList = new ArrayList();
			for (StructureHasAttribute structureHasAttribute : post.getStructure().getStructureHasAttributeList()) {
				AttributeDTO attributeDTO = new AttributeDTO();
				attributeDTO.setId(structureHasAttribute.getAttribute().getId());
				attributeDTO.setType(structureHasAttribute.getAttribute().getType());
				attributeDTO.setName(structureHasAttribute.getAttribute().getName());
				attributeDTOList.add(attributeDTO);
			}
			
			
			structureDTO.setAttributeList(attributeDTOList);
			postDTO.setStructure(structureDTO);
			
			//TODO: VERIFICARE il json document
			JSONParser parser = new JSONParser();
			Reader reader = new FileReader(post.getJsonDocument().getName());
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
			
			List<SkillDTO> skillDTOList = new ArrayList();
			
			for (PostRequireSkill postRequireSkill : post.getPostRequireSkillList()) {
				SkillDTO skillDTO = new SkillDTO();
				skillDTO.setId(postRequireSkill.getSkill().getId());
				skillDTO.setName(postRequireSkill.getSkill().getName());
				skillDTO.setDescription(postRequireSkill.getSkill().getDescription());
				
				skillDTOList.add(skillDTO);
			}
			
			postDTO.setSkillList(skillDTOList);
			
			
			RegularDTO regularDTO = new RegularDTO();
			regularDTO.setId(post.getCreatedBy().getId());
			regularDTO.setName(post.getCreatedBy().getName());
			regularDTO.setSurname(post.getCreatedBy().getSurname());
			regularDTO.setEmail(post.getCreatedBy().getEmail());
			
			postDTO.setCreatedBy(regularDTO);
			
			//TODO:COmmentList
			
			intrestedPostList.add(postDTO);
			
		}
		offerorDTO.setInterestedPostList(intrestedPostList);
		
		
		return offerorDTO;
	}

}
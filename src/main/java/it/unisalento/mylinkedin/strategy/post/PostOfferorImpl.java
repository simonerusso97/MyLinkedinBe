package it.unisalento.mylinkedin.strategy.post;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.AttachedDTO;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.dto.JsonDocumentDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
import it.unisalento.mylinkedin.dto.StructureDTO;
import it.unisalento.mylinkedin.dto.CommentDTO;
import it.unisalento.mylinkedin.iService.IAttachedService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.IUserService;

public class PostOfferorImpl implements GetPostStrategy{


	
	@Override
	public List<PostDTO> getAllPost(IPostService postService, IUserService userService, IAttachedService attachedService) throws FileNotFoundException, IOException, ParseException {
		List<Post> postList = postService.findAll();
		List<PostDTO> postDTOList = new ArrayList<>();
		for (Post post : postList) {
			if(!post.isHide()) {
				if(post.getCreatedBy().getClass() == Applicant.class) {
					PostDTO postDTO = new PostDTO();
					postDTO.setHide(post.isHide());
					postDTO.setId(post.getId());
					postDTO.setName(post.getName());
					postDTO.setPubblicationDate(post.getPubblicationDate());
					
					List<SkillDTO> skillDTOList = new ArrayList<>();
					
					List<PostRequireSkill> postRequireSkillList = post.getPostRequireSkillList();
					for (PostRequireSkill postrequireSkill : postRequireSkillList) {
						SkillDTO skillDTO = new SkillDTO();
						skillDTO.setDescription(postrequireSkill.getSkill().getDescription());
						skillDTO.setId(postrequireSkill.getSkill().getId());
						skillDTO.setName(postrequireSkill.getSkill().getName());
						
						skillDTOList.add(skillDTO);
					}
					
					postDTO.setSkillList(skillDTOList);
					
					ApplicantDTO applicantDTO = new ApplicantDTO();
					applicantDTO.setId(post.getCreatedBy().getId());
					applicantDTO.setName(post.getCreatedBy().getName());
					applicantDTO.setSurname(post.getCreatedBy().getSurname());
					applicantDTO.setEmail(post.getCreatedBy().getEmail());
					
					postDTO.setCreatedBy(applicantDTO);
					
					postDTOList.add(postDTO);
				}
			}
		}
		return postDTOList;
	}
	
}

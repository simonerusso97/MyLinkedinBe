package it.unisalento.mylinkedin.strategy.post;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
import it.unisalento.mylinkedin.dto.StructureDTO;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.IUserService;

public class PostAdminImpl implements GetPostStrategy{
	

	@Override
	public List<PostDTO> getAllPost(IPostService postService, IUserService userService) {
		List<Post> postList = postService.findAll();
		List<PostDTO> postDTOList = new ArrayList<>();
		for (Post post : postList) {
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
			
			StructureDTO structureDTO = new StructureDTO();
			structureDTO.setDescription(post.getStructure().getDescription());
			structureDTO.setId(post.getStructure().getId());
			structureDTO.setName(structureDTO.getName());
			postDTO.setStructure(structureDTO);
			
			RegularDTO regularDTO = new RegularDTO();
			regularDTO.setId(post.getCreatedBy().getId());
			regularDTO.setName(post.getCreatedBy().getName());
			regularDTO.setSurname(post.getCreatedBy().getSurname());
			regularDTO.setEmail(post.getCreatedBy().getEmail());
			
			postDTO.setCreatedBy(regularDTO);
						
			postDTOList.add(postDTO);
			
		}
		return postDTOList;
	}

}

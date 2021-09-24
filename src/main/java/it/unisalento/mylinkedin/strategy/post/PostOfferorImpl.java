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
					
					List<Comment> commentList=post.getCommentList();
					List<CommentDTO> answerList=new ArrayList<>();
					List<CommentDTO> commentDTOList=new ArrayList<>();
					
	                for (Comment comment : commentList) {
	                
	                	if(comment.getParent() == null) {
	                		
	                		CommentDTO commentDTO=new CommentDTO();
	                		commentDTO.setId(comment.getId());
	                		commentDTO.setDate(commentDTO.getDate());
	                		ApplicantDTO applicantDTO=new ApplicantDTO();
	                		applicantDTO.setId(comment.getApplicant().getId());
	                		applicantDTO.setName(comment.getApplicant().getName());
	                		applicantDTO.setSurname(comment.getApplicant().getSurname());
	                		applicantDTO.setType("applicant");
	                		commentDTO.setApplicant(applicantDTO);
	                		commentDTO.setText(comment.getText());
	                		for(Comment c: commentList) {
	                			if(c.getParent()==comment) {
	                			CommentDTO cDTO=new CommentDTO();
	                    		cDTO.setId(c.getId());
	                    		cDTO.setDate(c.getDate());
	                    		cDTO.setText(c.getText());
	                    		ApplicantDTO applicAnsDTO=new ApplicantDTO();
	                    		applicAnsDTO.setId(c.getApplicant().getId());
	                    		applicAnsDTO.setName(c.getApplicant().getName());
	                    		applicAnsDTO.setSurname(c.getApplicant().getSurname());
	                    		applicAnsDTO.setType("applicant");
	                    		answerList.add(cDTO);
	                    		}
	                		}
	                		commentDTO.setAnswerList(answerList);
	                		commentDTOList.add(commentDTO);
	                		}
	                }
	                postDTO.setCommentList(commentDTOList);
					
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
					
					List<Attached> attachedList = attachedService.findByPostIdAndType(post.getId(), "pdf");
					List<AttachedDTO> attachedDTOList = new ArrayList<>();
					AttachedDTO attachedDTO;
					for(Attached att : attachedList) {
						attachedDTO = new AttachedDTO();
						attachedDTO.setFilename(att.getName());
						attachedDTO.setId(att.getId());
						attachedDTO.setType(att.getType());
						
						attachedDTOList.add(attachedDTO);
					}
					
					postDTO.setAttachedDTOList(attachedDTOList);
				
					
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
					
					postDTOList.add(postDTO);
				}
			}
		}
		return postDTOList;
	}
	
}

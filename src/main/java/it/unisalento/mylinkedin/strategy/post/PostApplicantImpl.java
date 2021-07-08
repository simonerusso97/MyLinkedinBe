package it.unisalento.mylinkedin.strategy.post;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.dto.JsonDocumentDTO;
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
import it.unisalento.mylinkedin.dto.StructureDTO;
import it.unisalento.mylinkedin.dto.CommentDTO;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.ICompanyService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.IUserService;

public class PostApplicantImpl implements GetPostStrategy{

	@Override
	public List<PostDTO> getAllPost(IPostService postService, IUserService userService) throws FileNotFoundException, IOException, ParseException, UserNotFoundException {
		List<Post> postList = postService.findAll();
		List<PostDTO> postDTOList = new ArrayList<>();
		for (Post post : postList) {
			if(post.getCreatedBy().getClass() == Offeror.class) {
				PostDTO postDTO = new PostDTO();
				postDTO.setHide(post.isHide());
				postDTO.setId(post.getId());
				postDTO.setName(post.getName());
				postDTO.setPubblicationDate(post.getPubblicationDate());
				
				//ComponentDTO sezioneCommenti = new CompositeDTO();

				
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
                	postDTO.setCommentList(commentDTOList);
                } /*
        				ComponentDTO contenitoreThread = new CompositeDTO();

                        ComponentDTO contenitoreRisposte = new CompositeDTO();

                        sezioneCommenti.add(contenitoreThread);
                        
                		//SETTO IL PADRE
                        
                        ApplicantDTO applicantDTO=new ApplicantDTO();
                        applicantDTO.setId(comment.getApplicant().getId());
                        applicantDTO.setName(comment.getApplicant().getName());
                        applicantDTO.setSurname(comment.getApplicant().getSurname());
                        applicantDTO.setEmail(comment.getApplicant().getEmail());
                        ComponentDTO commentDTO=new CommentDTO(comment.getId(), comment.getDate(), comment.getText(),applicantDTO);

                        
                        contenitoreThread.add(commentDTO);

                        contenitoreThread.add(contenitoreRisposte);

                        //COSTRUISCO LA STRUTTURA CON LE SUE RISPOSTE
                        List<Comment> answerList = comment.getAnswerList();
                        
                        for (Comment answer : answerList) {
                        	ApplicantDTO appDTO = new ApplicantDTO();
                            appDTO.setId(answer.getApplicant().getId());
                            appDTO.setName(answer.getApplicant().getName());
                            appDTO.setSurname(answer.getApplicant().getSurname());
                            appDTO.setEmail(answer.getApplicant().getEmail());
                            
                        	CommentDTO answerDTO = new CommentDTO(answer.getId(), answer.getDate(),
                        			answer.getText(), appDTO);
                        	
                        	contenitoreRisposte.add(answerDTO);
    					}
                        
                        
                    }
                }

                postDTO.setCommentList(sezioneCommenti);
				*/
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
				
				OfferorDTO offerorDTO = new OfferorDTO();
				Offeror offeror = (Offeror) userService.findById(post.getCreatedBy().getId());
				offerorDTO.setId(offeror.getId());
				offerorDTO.setName(offeror.getName());
				offerorDTO.setSurname(offeror.getSurname());
				offerorDTO.setEmail(offeror.getEmail());
				CompanyDTO companyDTO = new CompanyDTO();
				companyDTO.setName(offeror.getCompany().getName());
				offerorDTO.setCompany(companyDTO);				
				
				postDTO.setCreatedBy(offerorDTO);
			
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
				
				//TODO: manca la commentLIST
				
				postDTOList.add(postDTO);
			}
		}
		return postDTOList;
	}

}

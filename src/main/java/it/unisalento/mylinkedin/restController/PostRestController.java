package it.unisalento.mylinkedin.restController;

import java.io.File;
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

import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.Skill;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.AttachedDTO;
import it.unisalento.mylinkedin.dto.CommentDTO;
import it.unisalento.mylinkedin.dto.JsonDocumentDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
import it.unisalento.mylinkedin.dto.StructureDTO;
import it.unisalento.mylinkedin.exceptions.CommentNotFoundException;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.IAttachedService;
import it.unisalento.mylinkedin.iService.ICommentService;
import it.unisalento.mylinkedin.iService.ICompanyService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.ISkillService;
import it.unisalento.mylinkedin.iService.IStructureService;
import it.unisalento.mylinkedin.iService.IUserService;
import it.unisalento.mylinkedin.strategy.post.PostAdminImpl;
import it.unisalento.mylinkedin.strategy.post.PostApplicantImpl;
import it.unisalento.mylinkedin.strategy.post.PostContext;
import it.unisalento.mylinkedin.strategy.post.PostOfferorImpl;
import java.util.Base64;
import java.nio.file.Files;

import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.Reader;
import java.util.Set;

@RestController
@RequestMapping("/post")
public class PostRestController {
	
	@Autowired
	IPostService postService;
	
	@Autowired
	ICompanyService companyService;
	
	@Autowired
	ISkillService skillService;
	
	@Autowired
	IStructureService structureService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	ICommentService commentService;
	
	@Autowired
	IAttachedService attachedService;

	
	  @RequestMapping(value="/findAllPost/{userType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE) 
	  public List<PostDTO> findAll(@PathVariable("userType") String userType) throws IOException, ParseException, UserNotFoundException{ 
		  PostContext postContext;
	  if(userType.equals("admin")) { 
		  postContext = new PostContext(new PostAdminImpl()); 
		  } 
	  else if(userType.equals("offeror")) { 
		  postContext = new PostContext(new PostOfferorImpl()); 
		  } 
	  else { 
		  postContext = new PostContext(new PostApplicantImpl()); 
		  } 
	  List<PostDTO> list = postContext.getAllPost(postService, userService, attachedService); 
	  return list; 
	  }
	 
	
	@RequestMapping(value="/changePostVisibility", method = RequestMethod.PATCH, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostDTO> hideShowPost(@RequestBody @Valid PostDTO postDTO) throws PostNotFoundException, OperationFailedException{
		
		
		Post post = postService.findById(postDTO.getId());
		post.setHide(postDTO.isHide());
		postService.save(post);
		return new ResponseEntity<PostDTO>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getAllSkill", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SkillDTO> findAll() throws OperationFailedException{
		List<Skill> skillList = skillService.findAll();
		List<SkillDTO> skillDTOList = new ArrayList<>();
		
		for (Skill skill : skillList) {
			SkillDTO skillDTO = new SkillDTO();
			skillDTO.setId(skill.getId());
			skillDTO.setName(skill.getName());
			skillDTO.setDescription(skill.getDescription());
			skillDTOList.add(skillDTO);
		}
		return skillDTOList;
	}
	@RequestMapping(value="/findPostById/{postId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public PostDTO findPostById(@PathVariable("postId") int postId) throws PostNotFoundException, UserNotFoundException, IOException, ParseException{
		Post post = postService.findById(postId);
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
		
		RegularDTO regularDTO = new RegularDTO();
		Regular regular = (Regular) userService.findById(post.getCreatedBy().getId());
		regularDTO.setId(regular.getId());
		regularDTO.setName(regular.getName());
		regularDTO.setSurname(regular.getSurname());
		regularDTO.setEmail(regular.getEmail());		
		
		postDTO.setCreatedBy(regularDTO);
		
		List<Comment> commentList=post.getCommentList();
		List<CommentDTO> answerList;
		List<CommentDTO> commentDTOList=new ArrayList<>();
		for (Comment comment : commentList) {
            answerList = new ArrayList<>();
        	if(comment.getParent() == null) {
        		
        		CommentDTO commentDTO=new CommentDTO();
        		commentDTO.setId(comment.getId());
        		commentDTO.setDate(comment.getDate());
        		ApplicantDTO applicantDTO=new ApplicantDTO();
        		applicantDTO.setId(comment.getApplicant().getId());
        		applicantDTO.setName(comment.getApplicant().getName());
        		applicantDTO.setSurname(comment.getApplicant().getSurname());
        		applicantDTO.setType("applicant");
        		commentDTO.setApplicant(applicantDTO);
        		commentDTO.setText(comment.getText());
        		//TODO: risposte ai commenti
				//List<Comment> childList = commentService.findChild(comment.getId());
        		//  for(Comment child: childList) {  
        		//	  CommentDTO childDTO=new CommentDTO(); 
        		//	  childDTO.setId(child.getId()); 
        		//	  childDTO.setDate(child.getDate());
        		//	  childDTO.setText(child.getText());
        		//	  ApplicantDTO applicAnsDTO=new ApplicantDTO();
        		//	  applicAnsDTO.setId(child.getApplicant().getId());
        		//	  applicAnsDTO.setName(child.getApplicant().getName());
        		//	  applicAnsDTO.setSurname(child.getApplicant().getSurname());
        		//	  applicAnsDTO.setType("applicant"); 
        		//	  childDTO.setApplicant(applicantDTO);
        		//	  answerList.add(childDTO);
        		//  }
				 
        		//commentDTO.setAnswerList(answerList);
        		commentDTOList.add(commentDTO);
        		
        	}
        	postDTO.setCommentList(commentDTOList);
		}
		List<Attached> attachedList = post.getAttachedList();
		List<AttachedDTO> attachedDTOList = new ArrayList<>(); 
		AttachedDTO attachedDTO;
		
		for (Attached attached : attachedList) {
			attachedDTO = new AttachedDTO();
			attachedDTO.setId(attached.getId());
			attachedDTO.setFilename(attached.getName());
			attachedDTO.setType(attached.getType());
			String code = encodeFileToBase64(new File("uploads/" + attached.getName()));
			attachedDTO.setCode(code);
			attachedDTOList.add(attachedDTO);
		}
		postDTO.setAttachedList(attachedDTOList);
		
		StructureDTO structureDTO = new StructureDTO();
		structureDTO.setDescription(post.getStructure().getDescription());
		structureDTO.setId(post.getStructure().getId());
		structureDTO.setName(structureDTO.getName());
		postDTO.setStructure(structureDTO);
		
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
		
		return postDTO;
	}
	
	@RequestMapping(value="/saveComment/{postId}", method = RequestMethod.PATCH, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommentDTO> saveComment(@PathVariable("postId") int postId, @RequestBody @Valid CommentDTO commentDTO) throws PostNotFoundException, UserNotFoundException{
	
		Comment comment = new Comment();
		comment.setDate(commentDTO.getDate());
		Applicant applicant = (Applicant) userService.findById(commentDTO.getApplicant().getId());
		comment.setApplicant(applicant);
		comment.setText(commentDTO.getText());
		Post post = postService.findById(postId);
		
		comment.setPost(post);
		
		commentService.save(comment);
		return new ResponseEntity<CommentDTO>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/saveComment/{postId}/{parentId}", method = RequestMethod.PATCH, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CommentDTO> saveComment(@PathVariable("postId") int postId, @PathVariable("parentId") int parentId, @RequestBody @Valid CommentDTO commentDTO) throws PostNotFoundException, UserNotFoundException, CommentNotFoundException{
	
		Comment comment = new Comment();
		comment.setDate(commentDTO.getDate());
		Applicant applicant = (Applicant) userService.findById(commentDTO.getApplicant().getId());
		comment.setApplicant(applicant);
		comment.setText(commentDTO.getText());
		Post post = postService.findById(postId);
		Comment parent = commentService.findById(parentId);
		comment.setParent(parent);
		comment.setPost(post);
		
		commentService.save(comment);
		return new ResponseEntity<CommentDTO>(HttpStatus.OK);
		
	}
	
	private static String encodeFileToBase64(File file) {
	    try {
	        byte[] fileContent = Files.readAllBytes(file.toPath());
	        return Base64.getEncoder().encodeToString(fileContent);
	    } catch (IOException e) {
	        throw new IllegalStateException("could not read file " + file, e);
	    }
	}
	
}

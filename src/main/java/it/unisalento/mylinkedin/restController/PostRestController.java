package it.unisalento.mylinkedin.restController;

import java.io.Console;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;

import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.domain.entity.JsonDocument;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.Skill;
import it.unisalento.mylinkedin.domain.entity.Structure;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.dto.ApplicantDTO;
import it.unisalento.mylinkedin.dto.AttachedDTO;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.dto.JsonDocumentDTO;
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.RegularDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
import it.unisalento.mylinkedin.dto.StructureDTO;
import it.unisalento.mylinkedin.dto.UserDTO;
import it.unisalento.mylinkedin.dto.CommentDTO;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.ICommentService;
import it.unisalento.mylinkedin.iService.ICompanyService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.ISkillService;
import it.unisalento.mylinkedin.iService.IStructureService;
import it.unisalento.mylinkedin.iService.IUserService;
import it.unisalento.mylinkedin.serviceImpl.UserServiceImpl;
import it.unisalento.mylinkedin.strategy.post.GetPostStrategy;
import it.unisalento.mylinkedin.strategy.post.PostAdminImpl;
import it.unisalento.mylinkedin.strategy.post.PostApplicantImpl;
import it.unisalento.mylinkedin.strategy.post.PostContext;
import it.unisalento.mylinkedin.strategy.post.PostOfferorImpl;

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
	
	@RequestMapping(value="/hideShowPost", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PostDTO> hideShowPost(@RequestBody @Valid PostDTO postDTO) throws OperationFailedException{
		Post post = postService.findById(postDTO.getId());
		post.setHide(postDTO.isHide());
		postService.updatePost(post);
		return new ResponseEntity<PostDTO>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/findAll", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PostDTO> findAll(@RequestBody @Valid UserDTO userDTO) throws IOException, ParseException, UserNotFoundException{
		PostContext postContext;
		if(userDTO.getType().equals("admin")) {
			 postContext = new PostContext(new PostAdminImpl());
		}
		else if(userDTO.getType().equals("offeror")) {
			 postContext = new PostContext(new PostOfferorImpl());
		}
		else {
			 postContext = new PostContext(new PostApplicantImpl());
		}
		List<PostDTO> list = postContext.getAllPost(postService, userService);
		return list;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Integer save(@RequestBody @Valid PostDTO postDTO) throws OperationFailedException, UserNotFoundException, IOException, JSONException{
		Post post = new Post();
		post.setHide(postDTO.isHide());
		post.setName(postDTO.getName());
		post.setPubblicationDate(postDTO.getPubblicationDate());
						
		List<SkillDTO> skillDTOList = postDTO.getSkillList();
		
		List<PostRequireSkill> postRequireSkillList = new ArrayList<>();
		
		for (SkillDTO skillDTO : skillDTOList) {
			Skill skill = skillService.findById(skillDTO.getId());
			PostRequireSkill postRequireSkill = new PostRequireSkill();
			postRequireSkill.setPost(post);
			postRequireSkill.setSkill(skill);
			
			postRequireSkillList.add(postRequireSkill);
		}
		
		post.setPostRequireSkillList(postRequireSkillList);
		
		Structure structure = structureService.getById(postDTO.getStructure().getId());
		
		post.setStructure(structure);
		
		Regular regular = userService.findById(postDTO.getCreatedBy().getId());
		post.setCreatedBy(regular);
		
		//TODO: manca la comment list
		//TODO: assicurarsi che la scrittura del file json funzioni
		
		int index = postService.getLastJsonDucumentIndex() + 1;
		String path = new File("").getAbsoluteFile()+"/jsonDocumentIndex"+index;
		
		JSONObject obj = new JSONObject();
		for (JsonDocumentDTO jsonDocument : postDTO.getJsonDocument()) {
			obj.put(jsonDocument.getNameAttribute(), jsonDocument.getValue());
		}
		
	    FileWriter file = new FileWriter(path);
        file.write(obj.valueToString(obj));
        file.close();
        
        post.setId(postService.save(post).getId());
        
        JsonDocument jsonDocument = new JsonDocument();
		jsonDocument.setName(path);
		jsonDocument.setPost(post);
		jsonDocument = postService.saveJsonDocument(jsonDocument);
		post.setJsonDocument(jsonDocument);
		
		
		
		

		return post.getId();
					
	}
	
	@RequestMapping(value="/getAllSkill", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<SkillDTO> findAll(){
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
	
	@RequestMapping(value="/UpdateCommentList/{id}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> UpdateCommentList(@PathVariable("id") int id, @RequestBody @Valid CommentDTO commentDTO) throws OperationFailedException, UserNotFoundException    {

        Post post=postService.findById(id);
        Comment comment=new Comment();
        comment.setId(commentDTO.getId());
        comment.setText(commentDTO.getText());
        comment.setDate(commentDTO.getDate());
        Applicant applicant=(Applicant) userService.findById(commentDTO.getApplicant().getId());
        comment.setApplicant(applicant);
        comment.setPost(post);

        postService.saveComment(comment);

        return new ResponseEntity<CommentDTO>(HttpStatus.OK);
    }
	
	@RequestMapping(value="/UpdateAnswerList/{id}", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommentDTO> UpdateAnswerList(@PathVariable("id") int id, @RequestBody @Valid CommentDTO commentDTO) throws OperationFailedException, UserNotFoundException    {

        Comment comment=commentService.findById(id); //commento padre originale
        
        
        Comment risposta=new Comment();
        
        risposta.setParent(comment);
        risposta.setDate(commentDTO.getDate());
        risposta.setPost(comment.getPost());
        risposta.setApplicant(comment.getApplicant());
        risposta.setText(commentDTO.getText());
        postService.saveComment(risposta);
        return new ResponseEntity<CommentDTO>(HttpStatus.OK);
    }


}

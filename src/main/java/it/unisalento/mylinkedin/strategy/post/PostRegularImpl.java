package it.unisalento.mylinkedin.strategy.post;

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
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.relationship.PostRequireSkill;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.dto.JsonDocumentDTO;
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.dto.SkillDTO;
import it.unisalento.mylinkedin.exceptions.UserNotFoundException;
import it.unisalento.mylinkedin.iService.IAttachedService;
import it.unisalento.mylinkedin.iService.IPostService;
import it.unisalento.mylinkedin.iService.IUserService;

public class PostRegularImpl implements GetPostStrategy{

	@Override
	public List<PostDTO> getAllPost(IPostService postService, IUserService userService, IAttachedService attachedService) throws FileNotFoundException, IOException, ParseException, UserNotFoundException {
		List<Post> postList = postService.findAll();
		List<PostDTO> postDTOList = new ArrayList<>();
		for (Post post : postList) {
			if(!post.isHide()) {
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
				Regular regular = userService.findById(post.getCreatedBy().getId());
				if(regular.getClass() == Offeror.class) {
					OfferorDTO offerorDTO = new OfferorDTO();
					Offeror offeror = (Offeror) regular;
					offerorDTO.setId(offeror.getId());
					offerorDTO.setName(offeror.getName());
					offerorDTO.setSurname(offeror.getSurname());
					offerorDTO.setEmail(offeror.getEmail());
					CompanyDTO companyDTO = new CompanyDTO();
					companyDTO.setName(offeror.getCompany().getName());
					offerorDTO.setCompany(companyDTO);				
					
					postDTO.setCreatedBy(offerorDTO);
				}
				
				
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
		return postDTOList;
	}

}

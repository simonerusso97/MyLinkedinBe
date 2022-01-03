package it.unisalento.mylinkedin.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.JsonDocumentRepository;
import it.unisalento.mylinkedin.dao.PostRepository;
import it.unisalento.mylinkedin.dao.RegularInterestedInPostRepository;
import it.unisalento.mylinkedin.domain.entity.JsonDocument;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;
import it.unisalento.mylinkedin.iService.IPostService;

@Service
public class PostService implements IPostService {

	@Autowired
	PostRepository postRepo;
	
	@Autowired
	RegularInterestedInPostRepository riipRepo;
	
	@Autowired
	JsonDocumentRepository jDocRepo;
	
	
	
	
	@Override
	public Post findById(int id) throws PostNotFoundException {
		return postRepo.findById(id).orElseThrow(() -> new PostNotFoundException());
	}

	@Override
	public Post save(Post post)  {
		try {
	        return postRepo.save(post);
		}catch (Exception e) {
			throw e;		
		}
	}

	@Override
	public List<Post> findAll() {
		try {
			return postRepo.findAll();
		} catch (Exception e) {
			throw e;		}
	}

	@Override
	public List<RegularInterestedInPost> findInterestedPost(int idUser) {
		try {
			return riipRepo.findByRegularId(idUser);
			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int getLastJsonDucumentIndex() {
		List<JsonDocument> list = jDocRepo.findAll();
		if(list.size() == 0) {
			return -1;
		}
		return list.get(list.size()-1).getId();	}

	@Override
	public JsonDocument saveJsonDocument(JsonDocument jsonDocument) {
		try {
			jDocRepo.save(jsonDocument);
		} catch (Exception e) {
			throw e;
		}
		return null;
	}


	@Override
	public List<RegularInterestedInPost> finAllInterestedNotNotified() {
		try {
			return riipRepo.findByNotified(false);
		}catch (Exception e) {
			throw e;
		}
	}

}

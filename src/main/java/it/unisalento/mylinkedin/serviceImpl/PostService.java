package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.PostRepository;
import it.unisalento.mylinkedin.dao.RegularInterestedInPostRepository;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;
import it.unisalento.mylinkedin.iService.IPostService;

@Service
public class PostService implements IPostService {

	@Autowired
	PostRepository postRepo;
	
	@Autowired
	RegularInterestedInPostRepository riipRepo;
	
	@Override
	public Post findById(int id) throws PostNotFoundException {
		return postRepo.findById(id).orElseThrow(() -> new PostNotFoundException());
	}

	@Override
	public void save(Post post) {
		try {
			postRepo.save(post);
		}
		catch (Exception e) {
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

}

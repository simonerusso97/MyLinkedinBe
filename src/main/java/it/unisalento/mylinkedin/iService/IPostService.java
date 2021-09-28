package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;

public interface IPostService {

	Post findById(int id) throws PostNotFoundException;

	void save(Post post);

	List<Post> findAll();

	List<RegularInterestedInPost> findInterestedPost(int idUser);

}

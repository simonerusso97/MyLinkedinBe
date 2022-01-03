package it.unisalento.mylinkedin.iService;

import java.util.Collection;
import java.util.List;

import it.unisalento.mylinkedin.domain.entity.JsonDocument;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.dto.PostDTO;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;

public interface IPostService {

	Post findById(int id) throws PostNotFoundException;

	Post save(Post post);

	List<Post> findAll();

	List<RegularInterestedInPost> findInterestedPost(int idUser);

	int getLastJsonDucumentIndex();

	JsonDocument saveJsonDocument(JsonDocument jsonDocument);


	List<RegularInterestedInPost> finAllInterestedNotNotified();

}

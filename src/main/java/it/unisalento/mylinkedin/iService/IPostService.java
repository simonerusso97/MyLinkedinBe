package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.domain.entity.JsonDocument;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;

public interface IPostService {

	Post findById(int id) throws PostNotFoundException;

	List<Post> findAll();

	int getLastJsonDucumentIndex();

	JsonDocument saveJsonDocument(JsonDocument jsonDocument);

	Post save(Post post);

	Comment saveComment(Comment comment);

}

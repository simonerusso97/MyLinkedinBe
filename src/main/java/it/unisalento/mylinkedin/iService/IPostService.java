package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.domain.entity.JsonDocument;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;

public interface IPostService {

	Post findById(int id) throws OperationFailedException;

	List<Post> findAll();

	int getLastJsonDucumentIndex();

	JsonDocument saveJsonDocument(JsonDocument jsonDocument) throws OperationFailedException;

	Post save(Post post) throws OperationFailedException;

	Comment saveComment(Comment comment) throws OperationFailedException;

}

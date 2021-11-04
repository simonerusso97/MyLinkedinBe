package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.exceptions.CommentNotFoundException;

public interface ICommentService {

	Comment save(Comment comment);

	List<Comment> findChild(int id);

	Comment findById(int parentId) throws CommentNotFoundException;

}

package it.unisalento.mylinkedin.iService;

import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;

public interface ICommentService {
	
	Comment findById(int id) throws OperationFailedException;

}

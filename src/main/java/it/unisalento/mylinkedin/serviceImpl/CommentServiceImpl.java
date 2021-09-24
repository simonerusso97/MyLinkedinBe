package it.unisalento.mylinkedin.serviceImpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.CommentRepository;
import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.exceptions.CommentNotFoundException;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.iService.ICommentService;

@Service
public class CommentServiceImpl implements ICommentService {

	@Autowired
	CommentRepository commentRepository;
	

	@Transactional
	public Comment findById(int id) throws CommentNotFoundException {
			return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException());
	}
}

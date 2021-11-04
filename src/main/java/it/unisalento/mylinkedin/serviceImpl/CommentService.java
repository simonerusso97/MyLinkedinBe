package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.CommentRepository;
import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.exceptions.CommentNotFoundException;
import it.unisalento.mylinkedin.iService.ICommentService;

@Service
public class CommentService implements ICommentService {

	@Autowired
	CommentRepository commentRepo;
	
	@Override
	public List<Comment> findChild(int id) {
		try {
			return commentRepo.findByParentId(id);
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public Comment findById(int parentId) throws CommentNotFoundException {
		return commentRepo.findById(parentId).orElseThrow(() -> new CommentNotFoundException());
	}
	
	@Override
	public Comment save(Comment comment) {
		try {
			 return commentRepo.save(comment);
		} catch (Exception e) {
			throw e;
		}
	}

}

package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.CommentRepository;
import it.unisalento.mylinkedin.dao.JsonDocumentRepository;
import it.unisalento.mylinkedin.dao.PostRepository;
import it.unisalento.mylinkedin.domain.entity.Comment;
import it.unisalento.mylinkedin.domain.entity.JsonDocument;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.iService.IPostService;

@Service
public class PostServiceImpl implements IPostService{

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	JsonDocumentRepository jsonDocumentRepository;
	
	@Autowired
	CommentRepository commentRepository;
	@Override
	public Post findById(int id) throws OperationFailedException {
		return postRepository.findById(id).orElseThrow(() -> new OperationFailedException());
	}


	@Override
	public List<Post> findAll() {
		return postRepository.findAll();
	}

	@Override
	public int getLastJsonDucumentIndex() {
		List<JsonDocument> list = jsonDocumentRepository.findAll();
		if(list.size() == 0) {
			return -1;
		}
		return list.get(list.size()-1).getId();
	}

	@Override
	public JsonDocument saveJsonDocument(JsonDocument jsonDocument) throws OperationFailedException {
		try {
			jsonDocumentRepository.save(jsonDocument);

		} catch (Exception e) {
			throw new OperationFailedException();
		}
		return null;
	}

	@Override
    @Transactional
    public Post save(Post post) throws OperationFailedException  {
        return postRepository.save(post);
	}

	@Override
	public Comment saveComment(Comment comment) throws OperationFailedException {
		try {
	        return commentRepository.save(comment);

		}catch (Exception e) {
			throw new OperationFailedException();
		}
	}

}

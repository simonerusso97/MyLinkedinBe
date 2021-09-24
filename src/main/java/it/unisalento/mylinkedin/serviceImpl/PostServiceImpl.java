package it.unisalento.mylinkedin.serviceImpl;

import java.util.ArrayList;
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
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;
import it.unisalento.mylinkedin.iService.IPostService;

@Service
public class PostServiceImpl implements IPostService{

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	JsonDocumentRepository jsonDocumentRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	/*
	 * @Override public Post findById(int id) throws PostNotFoundException { return
	 * postRepository.findById(id).orElseThrow(() -> new PostNotFoundException()); }
	 * 
	 * 
	 * @Override public List<Post> findAll() throws OperationFailedException { try {
	 * return postRepository.findAll(); }catch (Exception e) { throw new
	 * OperationFailedException(); } }
	 * 
	 * @Override public int getLastJsonDucumentIndex() throws
	 * OperationFailedException { List<JsonDocument> list = new ArrayList<>(); try {
	 * list = jsonDocumentRepository.findAll(); if(list.size() == 0) { return -1; }
	 * return list.get(list.size()-1).getId(); }catch (Exception e) { throw new
	 * OperationFailedException(); } }
	 * 
	 * @Override public JsonDocument saveJsonDocument(JsonDocument jsonDocument)
	 * throws OperationFailedException { try { return
	 * jsonDocumentRepository.save(jsonDocument); } catch (Exception e) { throw new
	 * OperationFailedException(); } }
	 * 
	 * @Override
	 * 
	 * @Transactional public Post save(Post post) throws OperationFailedException {
	 * try { return postRepository.save(post); } catch (Exception e) { throw new
	 * OperationFailedException(); } }
	 * 
	 * @Override public Comment saveComment(Comment comment) throws
	 * OperationFailedException { try { return commentRepository.save(comment);
	 * 
	 * }catch (Exception e) { throw new OperationFailedException(); } }
	 */
	
	@Override public Post findById(int id) throws PostNotFoundException { return
			 postRepository.findById(id).orElseThrow(() -> new PostNotFoundException()); 
	}
	
	@Override
	public List<Post> findAll() {
		try {
			return postRepository.findAll();
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int getLastJsonDucumentIndex(){
		List<JsonDocument> list = new ArrayList<>();
		try {
			list = jsonDocumentRepository.findAll();
			if(list.size() == 0) {
				return -1;
			}
			return list.get(list.size()-1).getId();
		}catch (Exception e) {
			throw e;
		}	
	}

	@Override
	public JsonDocument saveJsonDocument(JsonDocument jsonDocument){
		try {
			return jsonDocumentRepository.save(jsonDocument);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
    @Transactional
    public Post save(Post post){
		try {
			return postRepository.save(post);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Comment saveComment(Comment comment){
		try {
	        return commentRepository.save(comment);

		}catch (Exception e) {
			throw e;
		}
	}
}

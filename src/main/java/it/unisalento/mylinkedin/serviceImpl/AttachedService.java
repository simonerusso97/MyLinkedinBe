package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.AttachedRepository;
import it.unisalento.mylinkedin.dao.PostRepository;
import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.exceptions.AttachedNotFoundException;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;
import it.unisalento.mylinkedin.iService.IAttachedService;

@Service
public class AttachedService implements IAttachedService {
	
	@Autowired
	AttachedRepository attachedRepository;
	
	@Autowired
	PostRepository postRepository;

	/*
	 * @Override public Attached save(Attached attached) throws
	 * OperationFailedException{ try { return attachedRepository.save(attached); }
	 * catch (Exception e) { throw new OperationFailedException(); } }
	 * 
	 * @Override public List<Attached> findByPostIdAndType(int id, String type)
	 * throws PostNotFoundException{ postRepository.findById(id).orElseThrow(() ->
	 * new PostNotFoundException()); return
	 * attachedRepository.findByPostIdAndType(id, type); }
	 * 
	 * @Override public Attached findById(int id) throws AttachedNotFoundException{
	 * return attachedRepository.findById(id).orElseThrow(() -> new
	 * AttachedNotFoundException()); }
	 * 
	 * @Override public List<Attached> findByIdPost(int id) throws
	 * PostNotFoundException{ postRepository.findById(id).orElseThrow(() -> new
	 * PostNotFoundException()); return attachedRepository.findByPostId(id);
	 * 
	 * }
	 */

	@Override
	public Attached save(Attached attached){
		try {
			return attachedRepository.save(attached);
		} 
		catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Attached> findByPostIdAndType(int id, String type){
		try {
			postRepository.findById(id);
			return attachedRepository.findByPostIdAndType(id, type);
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Attached findById(int id) throws AttachedNotFoundException{
		return attachedRepository.findById(id).orElseThrow(() -> new AttachedNotFoundException());	
	}

	@Override
	public List<Attached> findByIdPost(int id){
		try {
			postRepository.findById(id);
			return attachedRepository.findByPostId(id);
		}
		catch (Exception e) {
			throw e;
		}
 
	}
}

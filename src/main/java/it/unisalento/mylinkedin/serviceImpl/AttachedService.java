package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.AttachedRepository;
import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.iService.IAttachedService;

@Service
public class AttachedService implements IAttachedService {
	
	@Autowired
	AttachedRepository attachedRepository;

	@Override
	public Attached save(Attached attached) {
		
		return attachedRepository.save(attached);
	}

	@Override
	public List<Attached> findByPostIdAndType(int id, String type) {
		return attachedRepository.findByPostIdAndType(id, type);
	}

	@Override
	public Attached findById(int id) throws OperationFailedException {
		return attachedRepository.findById(id).orElseThrow(() -> new OperationFailedException());	
	}

}

package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.AttributeRepository;
import it.unisalento.mylinkedin.domain.entity.Attribute;
import it.unisalento.mylinkedin.exceptions.AttributeNotFoundException;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.iService.IAttributeService;

@Service
public class AttributeServiceImpl implements IAttributeService {

	@Autowired
	AttributeRepository attributeRepository;
	
	@Override
	public Attribute findById(int id) throws AttributeNotFoundException{
		return attributeRepository.findById(id).orElseThrow(() -> new AttributeNotFoundException());
	}

	@Override
	public Attribute update(Attribute attribute) throws OperationFailedException {
		return attributeRepository.save(attribute);
	}

	@Override
	public void deleteAttribute(Attribute attribute) throws OperationFailedException {
		attributeRepository.delete(attribute);
	}
	
	@Override
	public List<Attribute> findAllAttribute(){
		return attributeRepository.findAll();
	}

	@Override
	@Transactional
	public Attribute save(Attribute attribute) throws OperationFailedException {
		try {
			return attributeRepository.save(attribute); 
		} catch (Exception e) {
			throw new OperationFailedException();
		}
	}
}

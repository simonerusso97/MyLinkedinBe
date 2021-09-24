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
	/*
	 * @Override public Attribute findById(int id) throws
	 * AttributeNotFoundException{ return
	 * attributeRepository.findById(id).orElseThrow(() -> new
	 * AttributeNotFoundException()); }
	 * 
	 * @Override public Attribute update(Attribute attribute) throws
	 * OperationFailedException { try { return attributeRepository.save(attribute);
	 * }catch (Exception e) { throw new OperationFailedException(); } }
	 * 
	 * @Override public void deleteAttribute(Attribute attribute) throws
	 * OperationFailedException{ try { attributeRepository.delete(attribute); }catch
	 * (Exception e) { throw new OperationFailedException(); }
	 * 
	 * }
	 * 
	 * @Override public List<Attribute> findAllAttribute() throws
	 * OperationFailedException{ try { return attributeRepository.findAll(); }catch
	 * (Exception e) { throw new OperationFailedException(); } }
	 * 
	 * @Override
	 * 
	 * @Transactional public Attribute save(Attribute attribute) throws
	 * OperationFailedException { try { return attributeRepository.save(attribute);
	 * } catch (Exception e) { throw new OperationFailedException(); } }
	 */
	

	@Override
	public Attribute findById(int id) throws AttributeNotFoundException{
		return attributeRepository.findById(id).orElseThrow(() -> new AttributeNotFoundException());
	}

	@Override
	public Attribute update(Attribute attribute){
		try {
			return attributeRepository.save(attribute);
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void deleteAttribute(Attribute attribute){
		try {
			attributeRepository.delete(attribute);
		}catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public List<Attribute> findAllAttribute(){
		try {
			return attributeRepository.findAll();
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public Attribute save(Attribute attribute) {
		try {
			return attributeRepository.save(attribute); 
		} catch (Exception e) {
			throw e;
		}
	}
}

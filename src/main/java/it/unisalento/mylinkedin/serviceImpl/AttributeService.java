package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.AttributeRepository;
import it.unisalento.mylinkedin.dao.StructureHasAttributeRepository;
import it.unisalento.mylinkedin.domain.entity.Attribute;
import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;
import it.unisalento.mylinkedin.exceptions.AttributeNotFoundException;
import it.unisalento.mylinkedin.iService.IAttributeService;

@Service
public class AttributeService implements IAttributeService {

	@Autowired
	AttributeRepository attributeRepo;
	
	@Autowired
	StructureHasAttributeRepository shaRepo;
	
	@Override
	public List<Attribute> findAllAttribute() {
		try {
			return attributeRepo.findAll();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Attribute findById(int id) throws AttributeNotFoundException {
		return attributeRepo.findById(id).orElseThrow(() -> new AttributeNotFoundException());
	}

	@Override
	public void deleteAttribute(Attribute attribute) {
		try {
			attributeRepo.delete(attribute);;
		} catch (Exception e) {
			throw e;
		}		
	}

	@Override
	public void save(Attribute attribute) {
		try {
			attributeRepo.save(attribute);
		} catch (Exception e) {
			throw e;
		}		
	}

	@Override
	public List<StructureHasAttribute> findByStructureId(int id) {
		try {
			return shaRepo.findByStructureId(id);
		} catch (Exception e) {
			throw e;
		}
	}

}

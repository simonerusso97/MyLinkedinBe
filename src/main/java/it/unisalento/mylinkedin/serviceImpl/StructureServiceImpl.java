package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.StructureRepository;
import it.unisalento.mylinkedin.domain.entity.Structure;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.iService.IStructureService;

@Service
public class StructureServiceImpl implements IStructureService {

	@Autowired
	StructureRepository structureRepository;
	
	@Override
	@Transactional(rollbackOn = OperationFailedException.class)
	public void delete(int id) throws OperationFailedException {
		Structure structure = structureRepository.findById(id).orElseThrow(()->new OperationFailedException());
		structureRepository.delete(structure);
	}

	@Override
	@Transactional
	public List<Structure> getAll(){ 
		return structureRepository.findAll();	
	}

	@Override
	@Transactional
	public Structure getById(int id) throws OperationFailedException{
		return structureRepository.findById(id).orElseThrow(()->new OperationFailedException());
	}

	@Override
	public Structure save(Structure structure) throws OperationFailedException {
		try {
			return structureRepository.save(structure);

		} catch (Exception e) {
			throw new OperationFailedException();
		}
	}

	@Override
	public List<Structure> findByUserType(String userType) {
		return structureRepository.findByUserTypeOrUserType(userType, "all");
	}

}

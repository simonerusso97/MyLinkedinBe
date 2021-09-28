package it.unisalento.mylinkedin.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.StructureHasAttributeRepository;
import it.unisalento.mylinkedin.dao.StructureRepository;
import it.unisalento.mylinkedin.domain.entity.Structure;
import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;
import it.unisalento.mylinkedin.exceptions.StructureNotFound;
import it.unisalento.mylinkedin.iService.IStructureService;

@Service
public class StructureService implements IStructureService {
	
	@Autowired
	StructureRepository structureRepo;
	
	@Autowired
	StructureHasAttributeRepository shaRepo;

	@Override
	public List<Structure> findByUserType(String userType) {
		try {
			return structureRepo.findByUserTypeOrUserType(userType, "all");
		}catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public void delete(int id) {
		try {
			structureRepo.deleteById(id);
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Structure> findAll() {
		try {
			return structureRepo.findAll();
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Structure getById(int id) throws StructureNotFound {
		return structureRepo.findById(id).orElseThrow(() -> new StructureNotFound());
	}

	@Override
	public void save(Structure structure) {
		try {
			structureRepo.save(structure);
		}catch (Exception e) {
			throw e;
		}		
	}

	@Override
	public void removeAttribute(List<StructureHasAttribute> toRemoveList) {
		try {
			List<Integer> ids = new ArrayList<>();
			for (StructureHasAttribute toRemove : toRemoveList) {
				ids.add(toRemove.getId());
			}
			shaRepo.deleteAllById(ids);
		}catch (Exception e) {
			throw e;
		}			
	}

}

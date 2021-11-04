package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Structure;
import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;
import it.unisalento.mylinkedin.exceptions.StructureNotFound;

public interface IStructureService {

	List<Structure> findByUserType(String userType);

	void delete(int id);

	List<Structure> findAll();

	Structure getById(int id) throws StructureNotFound;

	Structure save(Structure structure);

	void removeAttribute(List<StructureHasAttribute> toRemoveList);

}

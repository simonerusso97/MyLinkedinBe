package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Structure;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.StructureNotFound;

public interface IStructureService {
	public void delete(int id) throws StructureNotFound;
	public Structure save(Structure structure) throws OperationFailedException;
	public List<Structure> getAll();
	public Structure getById(int id) throws StructureNotFound;
	public List<Structure> findByUserType(String userType);

}

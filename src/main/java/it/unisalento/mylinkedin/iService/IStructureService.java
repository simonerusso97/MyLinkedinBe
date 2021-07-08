package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Structure;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;

public interface IStructureService {
	public void delete(int id) throws OperationFailedException;
	public Structure save(Structure structure) throws OperationFailedException;
	public List<Structure> getAll();
	public Structure getById(int id) throws OperationFailedException;
	public List<Structure> findByUserType(String userType);

}

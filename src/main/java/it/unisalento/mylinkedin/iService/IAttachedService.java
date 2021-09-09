package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;


public interface IAttachedService {
	
	Attached save(Attached attached);

	List<Attached> findByPostIdAndType(int id, String string);

	Attached findById(int id) throws OperationFailedException;

	List<Attached> findByIdPost(int id);

}

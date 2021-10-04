package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;

public interface IAttachedService {

	Attached save(Attached attached);

	List<Attached> findByPostIdAndType(int id, String type);

	Attached findById(int id) throws OperationFailedException, it.unisalento.mylinkedin.exceptions.OperationFailedException;

	List<Attached> findByIdPost(int id);

}

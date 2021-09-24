package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Attached;
import it.unisalento.mylinkedin.exceptions.AttachedNotFoundException;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.PostNotFoundException;


public interface IAttachedService {
	
	Attached save(Attached attached);

	List<Attached> findByPostIdAndType(int id, String string);

	Attached findById(int id) throws AttachedNotFoundException;

	List<Attached> findByIdPost(int id);

}

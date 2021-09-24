package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Attribute;
import it.unisalento.mylinkedin.exceptions.AttributeNotFoundException;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;

public interface IAttributeService {

	Attribute findById(int id) throws AttributeNotFoundException;

	Attribute update(Attribute attribute);

	void deleteAttribute(Attribute attribute);

	List<Attribute> findAllAttribute();

	Attribute save(Attribute attribute);

}

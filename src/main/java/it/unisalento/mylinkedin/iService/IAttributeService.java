package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Attribute;
import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;
import it.unisalento.mylinkedin.exceptions.AttributeNotFoundException;

public interface IAttributeService {

	List<Attribute> findAllAttribute();

	Attribute findById(int id) throws AttributeNotFoundException;

	void deleteAttribute(Attribute attribute);

	void save(Attribute attribute);

	List<StructureHasAttribute> findByStructureId(int id);

}

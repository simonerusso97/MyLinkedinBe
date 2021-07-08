package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Skill;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;

public interface ISkillService {

	Skill findById(int id) throws OperationFailedException;

	List<Skill> findAll();
	

}

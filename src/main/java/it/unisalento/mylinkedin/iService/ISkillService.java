package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Skill;
import it.unisalento.mylinkedin.exceptions.SkillNotFound;

public interface ISkillService {

	List<Skill> findAll();

	Skill findById(int id) throws SkillNotFound;

}

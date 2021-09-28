package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.SkillRepository;
import it.unisalento.mylinkedin.domain.entity.Skill;
import it.unisalento.mylinkedin.iService.ISkillService;

@Service
public class SkillService implements ISkillService {
	
	@Autowired
	SkillRepository skillRepo;

	@Override
	public List<Skill> findAll() {
		try {
			return skillRepo.findAll();
		}catch (Exception e) {
			throw e;
		}
	}

}

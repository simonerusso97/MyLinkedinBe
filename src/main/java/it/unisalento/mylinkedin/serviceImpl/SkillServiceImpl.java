package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.SkillRepository;
import it.unisalento.mylinkedin.domain.entity.Skill;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.iService.ISkillService;

@Service
public class SkillServiceImpl implements ISkillService{

	@Autowired
	SkillRepository skillRepository;

	/*
	 * @Override public Skill findById(int id) throws OperationFailedException {
	 * return skillRepository.findById(id).orElseThrow(() -> new
	 * OperationFailedException()); }
	 * 
	 * @Override public List<Skill> findAll() throws OperationFailedException { try
	 * { return skillRepository.findAll(); }catch (Exception e) { throw new
	 * OperationFailedException(); } }
	 */
	
	@Override
	public Skill findById(int id) throws OperationFailedException {
		return skillRepository.findById(id).orElseThrow(() -> new OperationFailedException());
	}

	@Override
	public List<Skill> findAll(){
		try {
			return skillRepository.findAll();
		}catch (Exception e) {
			throw e;
		}
	}
}

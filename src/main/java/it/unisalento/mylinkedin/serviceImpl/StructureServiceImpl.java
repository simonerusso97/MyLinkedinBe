package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.StructureRepository;
import it.unisalento.mylinkedin.domain.entity.Structure;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.StructureNotFound;
import it.unisalento.mylinkedin.iService.IStructureService;

@Service
public class StructureServiceImpl implements IStructureService {

	@Autowired
	StructureRepository structureRepository;
	
	/*
	 * @Override
	 * 
	 * @Transactional(rollbackOn = OperationFailedException.class) public void
	 * delete(int id) throws OperationFailedException, StructureNotFound { Structure
	 * structure = structureRepository.findById(id).orElseThrow(()->new
	 * StructureNotFound()); try { structureRepository.delete(structure); }catch
	 * (Exception e) { throw new OperationFailedException(); } }
	 * 
	 * @Override
	 * 
	 * @Transactional public List<Structure> getAll() throws
	 * OperationFailedException{ try { return structureRepository.findAll(); }catch
	 * (Exception e) { throw new OperationFailedException(); }
	 * 
	 * }
	 * 
	 * @Override
	 * 
	 * @Transactional public Structure getById(int id) throws StructureNotFound{
	 * return structureRepository.findById(id).orElseThrow(()->new
	 * StructureNotFound()); }
	 * 
	 * @Override
	 * 
	 * @Transactional public Structure save(Structure structure) throws
	 * OperationFailedException { try { return structureRepository.save(structure);
	 * } catch (Exception e) { throw e; //throw new OperationFailedException(); } }
	 * 
	 * @Override public List<Structure> findByUserType(String userType) { return
	 * structureRepository.findByUserTypeOrUserType(userType, "all"); }
	 */
	
	@Override
	@Transactional(rollbackOn = OperationFailedException.class)
	public void delete(int id) throws StructureNotFound {
		Structure structure = structureRepository.findById(id).orElseThrow(()->new StructureNotFound());
		try {
			
			structureRepository.delete(structure);
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public List<Structure> getAll(){
		try {
			return structureRepository.findAll();	
		}catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	@Transactional
	public Structure getById(int id) throws StructureNotFound{
		return structureRepository.findById(id).orElseThrow(()->new StructureNotFound());
	}

	@Override
	@Transactional
	public Structure save(Structure structure) throws OperationFailedException {
		try {
			return structureRepository.save(structure);
		} catch (Exception e) {
			throw e;
			//throw new OperationFailedException();
		}
	}

	@Override
	public List<Structure> findByUserType(String userType) {
		try {
			return structureRepository.findByUserTypeOrUserType(userType, "all");
		}catch (Exception e) {
			throw e;
		}
	}

}

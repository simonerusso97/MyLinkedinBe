package it.unisalento.mylinkedin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.mylinkedin.domain.entity.Structure;

@Repository
public interface StructureRepository extends JpaRepository<Structure, Integer>{
	
	public Structure findByName(String name);

	public List<Structure> findByUserTypeOrUserType(String userType1, String all);
	
	
}

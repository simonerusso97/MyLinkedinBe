package it.unisalento.mylinkedin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;

@Repository
public interface StructureHasAttributeRepository extends JpaRepository<StructureHasAttribute, Integer> {

	List<StructureHasAttribute> findByStructureId(int id);

}

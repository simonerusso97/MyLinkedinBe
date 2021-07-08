package it.unisalento.mylinkedin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import it.unisalento.mylinkedin.domain.entity.Attribute;





@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Integer>{



}
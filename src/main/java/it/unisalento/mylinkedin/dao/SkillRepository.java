package it.unisalento.mylinkedin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.mylinkedin.domain.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer>{

}

package it.unisalento.mylinkedin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.mylinkedin.domain.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>{

	Company findByNameAndPassword(String name, String pwd);

	Company findByName(String name);

}

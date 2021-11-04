package it.unisalento.mylinkedin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.unisalento.mylinkedin.domain.entity.Admin;
import it.unisalento.mylinkedin.domain.entity.Applicant;
import it.unisalento.mylinkedin.domain.entity.Offeror;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	List<Regular> findByDisabled(boolean disabled);

	User findByEmailAndPassword(String email, String pwd);

	Applicant findApplicantById(int id);

	Offeror findOfferorById(int id);

	Admin findAdminById(int id);

	Regular findByEmail(String email);

	List<Offeror> findByCompanyIdAndVerified(int idCompany, boolean ver);

}

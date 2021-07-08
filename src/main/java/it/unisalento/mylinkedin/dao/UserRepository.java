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
	public User findByEmailAndPassword(String email, String password);
	public Applicant findApplicantById(int idApplicant);
	public Offeror findOfferorById(int idOfferor);
	public Admin findAdminById(int id);
	public User findByEmail(String email);
	public List<Offeror> findByCompanyIdAndVerified(int idCompany, boolean verified);

}

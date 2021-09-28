package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.exceptions.CompanyAlreadyExist;
import it.unisalento.mylinkedin.exceptions.CompanyNotFound;

public interface ICompanyService {

	Company findById(int id) throws CompanyNotFound;

	Company findByNameAndPassword(String name, String pwd);

	List<Company> findAll();

	void findByName(String name) throws CompanyAlreadyExist;

	void save(Company company);

}

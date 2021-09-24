package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.exceptions.CompanyAlreadyExist;
import it.unisalento.mylinkedin.exceptions.CompanyNotFound;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;

public interface ICompanyService {

	Company save(Company company);

	Company login(String name, String pwd) throws CompanyNotFound;

	Company findById(int id) throws CompanyNotFound;

	void findByName(String name) throws CompanyAlreadyExist;

	List<Company> findAll();
	
}

package it.unisalento.mylinkedin.iService;

import java.util.List;

import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.UserAlreadyExist;

public interface ICompanyService {

	Company save(Company company) throws OperationFailedException;

	Company login(String name, String pwd) throws OperationFailedException;

	Company findById(int id) throws OperationFailedException;

	void findByName(String name) throws OperationFailedException, UserAlreadyExist;

	List<Company> findAll();
	
}

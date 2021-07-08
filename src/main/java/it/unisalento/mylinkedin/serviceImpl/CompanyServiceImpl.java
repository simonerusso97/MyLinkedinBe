package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.CompanyRepository;
import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.UserAlreadyExist;
import it.unisalento.mylinkedin.iService.ICompanyService;

@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	CompanyRepository companyRepository;

	@Override
	public Company save(Company company) throws OperationFailedException {
		try {
			return companyRepository.save(company);
		} catch (Exception e) {
			throw new OperationFailedException();
			}
	}

	@Override
	public Company login(String name, String pwd) throws OperationFailedException {
		
		Company company = companyRepository.findByNameAndPassword(name, pwd);
		if(company == null) {
			throw new OperationFailedException();
		}
		return company;
	}

	@Override
	public Company findById(int id) throws OperationFailedException {
		return companyRepository.findById(id).orElseThrow(() -> new OperationFailedException());
	}

	@Override
	public void findByName(String name) throws OperationFailedException, UserAlreadyExist {
		if( companyRepository.findByName(name) != null) {
			throw new UserAlreadyExist();
		}
	}

	@Override
	public List<Company> findAll() {
		return companyRepository.findAll();
	}	
	
	
	
}

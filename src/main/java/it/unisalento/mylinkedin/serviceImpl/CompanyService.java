package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.CompanyRepository;
import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.exceptions.CompanyAlreadyExist;
import it.unisalento.mylinkedin.exceptions.CompanyNotFound;
import it.unisalento.mylinkedin.iService.ICompanyService;

@Service
public class CompanyService implements ICompanyService {
	
	@Autowired
	CompanyRepository companyRepo;

	@Override
	public Company findById(int id) throws CompanyNotFound {
		return companyRepo.findById(id).orElseThrow(() -> new CompanyNotFound());
	}

	@Override
	public Company findByNameAndPassword(String name, String pwd) {
		try {
			return companyRepo.findByNameAndPassword(name, pwd);
		}catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<Company> findAll() {
		try {
			return companyRepo.findAll();
		}catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public void findByName(String name) throws CompanyAlreadyExist {
		try {
			companyRepo.findByName(name);
		}catch (Exception e) {
			throw new CompanyAlreadyExist();
		}
		
	}

	@Override
	public void save(Company company) {
		try {
			companyRepo.save(company);
		}catch (Exception e) {
			throw e;
		}
	}

	
	
	

}

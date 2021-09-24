package it.unisalento.mylinkedin.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.unisalento.mylinkedin.dao.CompanyRepository;
import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.exceptions.CompanyAlreadyExist;
import it.unisalento.mylinkedin.exceptions.CompanyNotFound;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.iService.ICompanyService;

@Service
public class CompanyServiceImpl implements ICompanyService {

	@Autowired
	CompanyRepository companyRepository;

	/*
	 * @Override public Company save(Company company){ try { return
	 * companyRepository.save(company); } catch (Exception e) { throw e; } }
	 * 
	 * @Override public Company login(String name, String pwd) throws
	 * CompanyNotFound {
	 * 
	 * Company company = companyRepository.findByNameAndPassword(name, pwd);
	 * if(company == null) { throw new CompanyNotFound(); } return company; }
	 * 
	 * @Override public Company findById(int id) throws CompanyNotFound { return
	 * companyRepository.findById(id).orElseThrow(() -> new CompanyNotFound()); }
	 * 
	 * @Override public void findByName(String name) throws CompanyAlreadyExist {
	 * if(companyRepository.findByName(name) != null) { throw new
	 * CompanyAlreadyExist(); } }
	 * 
	 * @Override public List<Company> findAll() throws OperationFailedException {
	 * try { return companyRepository.findAll(); } catch (Exception e) { throw new
	 * OperationFailedException(); } }
	 * 
	 */
	
	@Override
	public Company save(Company company){
		try {
			return companyRepository.save(company);
		} catch (Exception e) {
			throw e;
			}
	}

	@Override
	public Company login(String name, String pwd) throws CompanyNotFound {
		
		Company company = companyRepository.findByNameAndPassword(name, pwd);
		if(company == null) {
			throw new CompanyNotFound();
		}
		return company;
	}

	@Override
	public Company findById(int id) throws CompanyNotFound {
		return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFound());
	}

	@Override
	public void findByName(String name) throws CompanyAlreadyExist {
		if(companyRepository.findByName(name) != null) {
			throw new CompanyAlreadyExist();
		}
	}

	@Override
	public List<Company> findAll() {
		try {
			return companyRepository.findAll();
		} catch (Exception e) {
			throw e;
			}
	}	
	
}

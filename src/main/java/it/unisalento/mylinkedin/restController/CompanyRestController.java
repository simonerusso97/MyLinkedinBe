package it.unisalento.mylinkedin.restController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.exceptions.CompanyAlreadyExist;
import it.unisalento.mylinkedin.exceptions.CompanyNotFound;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.iService.ICompanyService;

@RestController
@RequestMapping("/company")
public class CompanyRestController {
	
	@Autowired
	ICompanyService companyService;
	
	@RequestMapping(value="/login/{name}/{pwd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private CompanyDTO login(@PathVariable("name") String name, @PathVariable("pwd") String pwd) throws CompanyNotFound{
		Company company = companyService.findByNameAndPassword(name, pwd);
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());
		companyDTO.setDescription(company.getDescription());
		companyDTO.setSector(companyDTO.getSector());
		companyDTO.setPassword(company.getPassword());
		return companyDTO;
	}
	
	@RequestMapping(value="/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<CompanyDTO> findAll() throws OperationFailedException{
		List<Company> companyList = companyService.findAll();
		List<CompanyDTO> companyDTOList = new ArrayList<>();
		for (Company company : companyList) {
			CompanyDTO companyDTO = new CompanyDTO();
			companyDTO.setId(company.getId());
			companyDTO.setName(company.getName());
			companyDTO.setPassword(company.getPassword());
			companyDTO.setSector(company.getSector());
			companyDTO.setDescription(company.getDescription());
			companyDTOList.add(companyDTO);
			}
		return companyDTOList;
	}
	
	@RequestMapping(value="/companySignUp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<CompanyDTO> registrationCompany(@RequestBody CompanyDTO companyDTO) throws CompanyAlreadyExist, OperationFailedException{	
		
		Company company = new Company();
		
		companyService.findByName(companyDTO.getName().toUpperCase());
		company.setName(companyDTO.getName().toUpperCase());
		company.setDescription(companyDTO.getDescription());
		company.setPassword(companyDTO.getPassword());
		company.setSector(companyDTO.getSector());
		companyService.save(company);
		return new ResponseEntity<CompanyDTO>(HttpStatus.CREATED);
	}
}

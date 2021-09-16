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
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.UserAlreadyExist;
import it.unisalento.mylinkedin.iService.ICompanyService;

@RestController
@RequestMapping("/company")
public class CompanyRestController {
	
	@Autowired
	ICompanyService companyService;
	
	@RequestMapping(value="/login/{name}/{pwd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private CompanyDTO login(@PathVariable("name") String name, @PathVariable("pwd") String pwd) throws OperationFailedException{
		Company company = companyService.login(name, pwd);
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(company.getId());
		companyDTO.setName(company.getName());
		companyDTO.setDescription(company.getDescription());
		companyDTO.setSector(companyDTO.getSector());
		companyDTO.setPassword(company.getPassword());
		List<OfferorDTO> offerorDTOList = new ArrayList<OfferorDTO>();
		company.getOfferorList().forEach(offeror -> {
			OfferorDTO offerorDTO = new OfferorDTO();
			offerorDTO.setId(offeror.getId());
			offerorDTO.setName(offeror.getName());
			offerorDTO.setSurname(offeror.getSurname());
			offerorDTO.setEmail(offeror.getEmail());
			offerorDTO.setPosition(offeror.getPosition());
			offerorDTO.setVerified(offeror.isVerified());
		});
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
			
			companyDTOList.add(companyDTO);
			}
		return companyDTOList;
	}
	
	@RequestMapping(value="/registrationCompany", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<CompanyDTO> registrationCompany(@RequestBody CompanyDTO companyDTO){	
		try {
			Company company = new Company();
			
			companyService.findByName(companyDTO.getName());
			
			company.setName(companyDTO.getName().toUpperCase());
			company.setDescription(companyDTO.getDescription());
			company.setPassword(companyDTO.getPassword());
			company.setSector(companyDTO.getSector());
			companyService.save(company);
			}
		catch (Exception e) {
			if(e.getClass() == OperationFailedException.class) {
				return new ResponseEntity<CompanyDTO>(HttpStatus.NO_CONTENT);
			}
			else if(e.getClass() == UserAlreadyExist.class) {
				return new ResponseEntity<CompanyDTO>(HttpStatus.NO_CONTENT);
			}
		}
		return new ResponseEntity<CompanyDTO>(HttpStatus.CREATED);
	}
}

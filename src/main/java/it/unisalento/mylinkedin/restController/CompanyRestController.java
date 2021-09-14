package it.unisalento.mylinkedin.restController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

import it.unisalento.mylinkedin.domain.entity.Company;
import it.unisalento.mylinkedin.dto.CompanyDTO;
import it.unisalento.mylinkedin.dto.OfferorDTO;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
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
}

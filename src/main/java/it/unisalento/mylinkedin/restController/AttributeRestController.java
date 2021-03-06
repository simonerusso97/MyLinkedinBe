package it.unisalento.mylinkedin.restController;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.mylinkedin.iService.IAttributeService;

import it.unisalento.mylinkedin.domain.entity.Attribute;
import it.unisalento.mylinkedin.dto.AttributeDTO;
import it.unisalento.mylinkedin.exceptions.AttributeNotFoundException;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;

@RestController
@RequestMapping("/attribute")
public class AttributeRestController {
	
	@Autowired
	IAttributeService attributeService;
	
	@RequestMapping(value = "/findAllAttribute", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	private List<AttributeDTO> findAllAttribute() throws OperationFailedException{
		List<Attribute> attributeList = attributeService.findAllAttribute();
		List<AttributeDTO> attributeDTOList = new ArrayList<>();
		for (Attribute attribute : attributeList) {
			AttributeDTO attributeDTO = new AttributeDTO();
			attributeDTO.setId(attribute.getId());
			attributeDTO.setName(attribute.getName());
			attributeDTO.setType(attribute.getType());
			attributeDTO.setDeletable(attribute.isDeletable());
			attributeDTOList.add(attributeDTO);
		}
		return attributeDTOList;
	}
	
	@RequestMapping(value = "/deleteAttribute/{id}", method = RequestMethod.DELETE)
	private ResponseEntity<AttributeDTO> deleteAttribure(@PathVariable("id") int id) throws AttributeNotFoundException{
		Attribute attribute = attributeService.findById(id);
		attributeService.deleteAttribute(attribute);
		return new ResponseEntity<AttributeDTO>(HttpStatus.ACCEPTED);
 
	}
	
	@RequestMapping(value="/updateAttribute", method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
	private ResponseEntity<AttributeDTO> updateAttribute(@RequestBody AttributeDTO attributeDTO) throws AttributeNotFoundException{
		Attribute attribute = attributeService.findById(attributeDTO.getId());
		attribute.setId(attributeDTO.getId());
		attribute.setName(attributeDTO.getName());
		attribute.setType(attributeDTO.getType());
		attributeService.save(attribute);
		return new ResponseEntity<AttributeDTO>(HttpStatus.OK);
	}
	
	@PostMapping(value="/createAttribute", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AttributeDTO> createNewAttribute(@RequestBody @Valid AttributeDTO attributeDTO){
		Attribute attribute=new Attribute();
		attribute.setName(attributeDTO.getName());
		attribute.setType(attributeDTO.getType());
		attributeService.save(attribute);
		return new ResponseEntity<AttributeDTO>(HttpStatus.CREATED);
	}
	
}

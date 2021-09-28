package it.unisalento.mylinkedin.restController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.mylinkedin.dao.AttributeRepository;
import it.unisalento.mylinkedin.domain.entity.Attribute;
import it.unisalento.mylinkedin.domain.entity.Post;
import it.unisalento.mylinkedin.domain.entity.Regular;
import it.unisalento.mylinkedin.domain.entity.Structure;
import it.unisalento.mylinkedin.domain.relationship.RegularInterestedInPost;
import it.unisalento.mylinkedin.domain.relationship.StructureHasAttribute;
import it.unisalento.mylinkedin.dto.AttributeDTO;
import it.unisalento.mylinkedin.dto.StructureDTO;
import it.unisalento.mylinkedin.exceptions.AttributeNotFoundException;
import it.unisalento.mylinkedin.exceptions.OperationFailedException;
import it.unisalento.mylinkedin.exceptions.StructureNotFound;
import it.unisalento.mylinkedin.iService.IAttributeService;
import it.unisalento.mylinkedin.iService.IStructureService;

@RestController
@RequestMapping("/structure")
public class StructureRestController {


	@Autowired
	IStructureService structureService;
	
	@Autowired
	IAttributeService attributeService;
	
	@RequestMapping(value="/getAll", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<StructureDTO> getAllStructure(){
			
			List<StructureDTO> structureDTOList = new ArrayList<>();
			List<Structure> structureList = structureService.findAll(); 
			
			for (Structure structure : structureList) {
				StructureDTO structureDTO = new StructureDTO();
				List<AttributeDTO> attributeDTOList = new ArrayList<>();				
				
				structureDTO.setId(structure.getId());
				structureDTO.setName(structure.getName());
				structureDTO.setDescription(structure.getDescription());
				structureDTO.setUserType(structure.getUserType());
				structureDTO.setDeletable(structure.isDeletable());
				
				structure.getStructureHasAttributeList().forEach(
						(structureHasAttribute) -> {
							Attribute attribute = structureHasAttribute.getAttribute();
							AttributeDTO attributeDTO = new AttributeDTO();
							attributeDTO.setId(attribute.getId());
							attributeDTO.setName(attribute.getName());
							attributeDTO.setType(attribute.getType());
							attributeDTO.setDeletable(attribute.isDeletable());
							
							attributeDTOList.add(attributeDTO);
						});
				structureDTO.setAttributeList(attributeDTOList);
				structureDTOList.add(structureDTO);
			}
			return structureDTOList;
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<StructureDTO> delete(@PathVariable int id) throws OperationFailedException, StructureNotFound{
		
		structureService.delete(id);
		
		return new ResponseEntity<StructureDTO>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StructureDTO> update(@RequestBody @Valid StructureDTO structureDTO) throws OperationFailedException, AttributeNotFoundException, StructureNotFound{
		boolean contained;
		List<StructureHasAttribute> structureHasAttributeList = new ArrayList<>();
		
		Structure structure = structureService.getById(structureDTO.getId());
		structure.setName(structureDTO.getName());
		structure.setDescription(structureDTO.getDescription());
		structure.setUserType(structureDTO.getUserType());
		
		StructureHasAttribute sha;
		List<StructureHasAttribute> shaList = new ArrayList<>();
		Attribute attribute;
		boolean found = false;
		

	
		List<AttributeDTO> attributeListTemp = new ArrayList<>();
		attributeListTemp.addAll(structureDTO.getAttributeList());
		
		List<StructureHasAttribute> updatedList = new ArrayList<>();
		List<StructureHasAttribute> toRemoveList = new ArrayList<>();

		
		for (StructureHasAttribute originalList : structure.getStructureHasAttributeList()) {
			for (AttributeDTO attributeDTO : structureDTO.getAttributeList()) {
				if(attributeDTO.getId() == originalList.getAttribute().getId()) {
					updatedList.add(originalList);
					attributeListTemp.remove(attributeDTO);
					found = true;
					break;
				}
			}
			if(!found) {
				toRemoveList.add(originalList);
			}
			found = false;
			
		}
		shaList.addAll(updatedList);
		for (AttributeDTO attributeDTO : attributeListTemp) {
			sha = new StructureHasAttribute();
			attribute = attributeService.findById(attributeDTO.getId());
			sha.setAttribute(attribute);
			sha.setStructure(structure);
			shaList.add(sha);
		}
		structure.setStructureHasAttributeList(shaList);
		
		structureService.save(structure);
		structureService.removeAttribute(toRemoveList);
		
		return new ResponseEntity<StructureDTO>(HttpStatus.OK);		
	}

	@PostMapping(value="/createStructure", consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Structure> createStructure(@RequestBody @Valid StructureDTO structureDTO) throws AttributeNotFoundException, OperationFailedException{
		
		List<StructureHasAttribute> structureHasAttributeList = new ArrayList<>();
		
		Structure structure = new Structure();
		structure.setName(structureDTO.getName());
		structure.setDescription(structureDTO.getDescription());
		structure.setUserType(structureDTO.getUserType());
		
		for (AttributeDTO attributeDTO : structureDTO.getAttributeList()) {
			StructureHasAttribute structureHasAttribute = new StructureHasAttribute();
			
			Attribute attribute = attributeService.findById(attributeDTO.getId());
		
			structureHasAttribute.setAttribute(attribute);
			
			structureHasAttributeList.add(structureHasAttribute);
			structureHasAttribute.setStructure(structure);
			attribute.getStructureHasAttributeList().add(structureHasAttribute);			
		}
		structure.setStructureHasAttributeList(structureHasAttributeList);;		
		structureService.save(structure);
	
	
		return new ResponseEntity<Structure>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/getByType/{userType}", method = RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public List<StructureDTO> getByType(@PathVariable("userType") String userType){
		List<Structure> structureList = structureService.findByUserType(userType);
		List<StructureDTO> structureDTOList = new ArrayList<>();
		List<AttributeDTO> attributeDTOList = new ArrayList<>();


		for (Structure structure : structureList) {
			StructureDTO structureDTO = new StructureDTO();
			structureDTO.setId(structure.getId());
			structureDTO.setName(structure.getName());
			structureDTO.setDescription(structure.getDescription());
			structureDTO.setUserType(structure.getUserType());
			structureDTO.setDeletable(structure.isDeletable());
			
			structure.getStructureHasAttributeList().forEach(
					(structureHasAttribute) -> {
						Attribute attribute = structureHasAttribute.getAttribute();
						AttributeDTO attributeDTO = new AttributeDTO();
						attributeDTO.setId(attribute.getId());
						attributeDTO.setName(attribute.getName());
						attributeDTO.setType(attribute.getType());
						attributeDTO.setDeletable(structureHasAttribute.isDeletable());
						
						attributeDTOList.add(attributeDTO);
					});
			structureDTO.setAttributeList(attributeDTOList);
			structureDTOList.add(structureDTO);
		}
		return structureDTOList;
	}
}

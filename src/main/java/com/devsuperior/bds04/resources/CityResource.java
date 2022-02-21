package com.devsuperior.bds04.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.services.CityService;
import com.devsuperior.bds04.services.exceptions.DatabaseException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;



@RestController
@RequestMapping(value = "/cities")
public class CityResource {
	
	@Autowired
	private CityService service;

	@GetMapping
	public ResponseEntity<List<CityDTO>> findAll() {

		List<CityDTO> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@PostMapping
	public ResponseEntity<CityDTO> insert(@Valid @RequestBody CityDTO dto) {
		try {dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);}
		catch(ResourceNotFoundException e) {
			return ResponseEntity.badRequest().build();
		}
	
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<CityDTO> delete(@PathVariable Long id) {
		try{service.delete(id);}
		catch(ResourceNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
		catch(DatabaseException e) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.noContent().build();

	}
	

		
	
}

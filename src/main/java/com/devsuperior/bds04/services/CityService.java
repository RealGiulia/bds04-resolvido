package com.devsuperior.bds04.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exceptions.DatabaseException;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;
import com.devsuperior.bds04.services.exceptions.UnauthorizedException;

@Service
public class CityService {
	@Autowired
	private CityRepository repository;


	@Transactional
	public List<CityDTO> findAll() {
		List<City> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList());
	}

	
	@Transactional
	public CityDTO insert(CityDTO dto) {
		try {
		City entity = new City();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CityDTO(entity);}
		catch(UnauthorizedClientException e) {
			throw new UnauthorizedException("User not logged");
			
		}

	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id not found " + id);
		}
		catch(DataIntegrityViolationException e){
			throw new DatabaseException("Integrity violation");
			
		}
	}
	
	@Transactional
	public CityDTO update(Long id, CityDTO dto) {
		try {
		City entity = repository.getOne(id);
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new CityDTO(entity);}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}
	}
	
	private void copyDtoToEntity(CityDTO dto, City entity) {
		
		entity.setName(dto.getName());		
	}
}
package com.example.domains.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.domains.contracts.repositories.LanguageRepository;
import com.example.domains.contracts.services.LanguageService;
import com.example.domains.entities.Language;
import com.example.exceptions.DuplicateKeyException;
import com.example.exceptions.InvalidDataException;
import com.example.exceptions.NotFoundException;

@Service

public class LanguageServiceImpl implements LanguageService {

	private LanguageRepository dao;
	
	
	public LanguageServiceImpl(LanguageRepository dao) {
		this.dao = dao;
	}

	@Override
	public <T> List<T> getByProjection(Class<T> type) {
		return dao.findAllBy(type);
	}

	@Override
	public <T> Iterable<T> getByProjection(Sort sort, Class<T> type) {
		return dao.findAllBy(sort, type);
	}

	@Override
	public <T> Page<T> getByProjection(Pageable pageable, Class<T> type) {
		return dao.findAllBy(pageable, type);
	}

	@Override
	public Iterable<Language> getAll(Sort sort) {
		return dao.findAll(sort);
	}

	@Override
	public Page<Language> getAll(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public List<Language> getAll() {
		return dao.findAll();
	}

	@Override
	public Optional<Language> getOne(Integer id) {
		return dao.findById(id);
	}

	@Override
	public Language add(Language item) throws DuplicateKeyException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("No puede ser nulo");
		}
		if(item.isInvalid()) {
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		}
		if(item.getLanguageId() != 0 && dao.existsById(item.getLanguageId())) {
			throw new DuplicateKeyException("Ya existe");
		}
		return dao.save(item);
	}

	@Override
	public Language modify(Language item) throws NotFoundException, InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("No puede ser nulo");
		}
		if(item.isInvalid()) {
			throw new InvalidDataException(item.getErrorsMessage(), item.getErrorsFields());
		}
		if(item.getLanguageId() == 0 && !dao.existsById(item.getLanguageId())) {
			throw new NotFoundException("No existe");
		}
		return dao.save(item);
	}

	@Override
	public void delete(Language item) throws InvalidDataException {
		if(item == null) {
			throw new InvalidDataException("No puede ser nulo");
		}
		dao.delete(item);
		
	}

	@Override
	public void deleteById(Integer id) {
		dao.deleteById(id);
		
	}
	
	@Override
	public List<Language> novedades(Timestamp fecha) {
		return dao.findByLastUpdateGreaterThanEqualOrderByLastUpdate(fecha);
	}

}

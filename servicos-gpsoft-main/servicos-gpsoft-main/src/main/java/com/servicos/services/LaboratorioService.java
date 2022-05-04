package com.servicos.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.servicos.dto.LaboratorioDTO;
import com.servicos.entity.Laboratorio;
import com.servicos.repositories.LaboratorioRepository;
import com.servicos.services.exception.ConstraintVException;
import com.servicos.services.exception.DataIntegrityException;
import com.servicos.services.exception.ObjectNFException;

@Service
public class LaboratorioService {
	@Autowired
	private LaboratorioRepository repo;

	public Laboratorio findById(Integer id) {
		Optional<Laboratorio> objLaboratorio = repo.findById(id);

		return objLaboratorio.orElseThrow(() -> new ObjectNFException("Laboratório não encontrado! Id: " + id));
	}

	public List<Laboratorio> findAll() {
		List<Laboratorio> listLaboratorio = repo.findAll();
		return listLaboratorio;
	}

	public Laboratorio insert(Laboratorio obj) {
		obj.setId(null);
		try {
			repo.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Laboratório já cadastrado!");
		}
		return obj;
	}

	public Laboratorio update(Laboratorio obj) {
		findById(obj.getId());
		try {
			repo.save(obj);
		} catch (DataIntegrityViolationException e) {
			if (e.getMostSpecificCause().getMessage().contains("Unique")) {
				throw new ConstraintVException("Laboratório já cadastrado!");
			}

		}
		return obj;
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma Laboratório que está em uso!");
		}
	}

	public Page<Laboratorio> findPage(String search, Integer page, Integer size, String orderBy, String direction) {
		Pageable pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);

		return repo.findByNomePagination(search, pageRequest);
	}

	public Laboratorio fromDTO(LaboratorioDTO objDTO) {
		return Laboratorio.builder().id(objDTO.getId()).nome(objDTO.getNome()).build();
	}
}

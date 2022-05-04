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

import com.servicos.dto.PropriedadeDTO;
import com.servicos.entity.Propriedade;
import com.servicos.repositories.PropriedadeRepository;
import com.servicos.services.exception.ConstraintVException;
import com.servicos.services.exception.DataIntegrityException;
import com.servicos.services.exception.ObjectNFException;

@Service
public class PropriedadeService {
	@Autowired
	private PropriedadeRepository repo;

	public Propriedade findById(Integer id) {
		Optional<Propriedade> objPropriedade = repo.findById(id);

		return objPropriedade.orElseThrow(() -> new ObjectNFException("Propriedade não encontrado! Id: " + id));
	}

	public List<Propriedade> findAll() {
		List<Propriedade> listPropriedade = repo.findAll();
		return listPropriedade;
	}

	public Propriedade insert(Propriedade obj) {
		obj.setId(null);
		try {
			repo.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Propriedade já cadastrada!");
		}
		return obj;
	}

	public Propriedade update(Propriedade obj) {
		findById(obj.getId());
		try {
			repo.save(obj);
		} catch (DataIntegrityViolationException e) {
			if (e.getMostSpecificCause().getMessage().contains("Unique")) {
				throw new ConstraintVException("Propriedade já cadastrada!");
			}

		}
		return obj;
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma propriedade que está em uso!");
		}
	}

	public Page<Propriedade> findPage(String search, Integer page, Integer size, String orderBy, String direction) {
		Pageable pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);

		return repo.findByNomePagination(search, pageRequest);
	}

	public Propriedade fromDTO(PropriedadeDTO objDTO) {
		return Propriedade.builder().id(objDTO.getId()).nome(objDTO.getNome()).cnpj(objDTO.getCnpj()).build();
	}
}

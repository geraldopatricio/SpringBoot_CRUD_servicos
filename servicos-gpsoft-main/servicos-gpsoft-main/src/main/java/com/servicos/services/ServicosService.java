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

import com.servicos.dto.ServicosDTO;
import com.servicos.entity.Laboratorio;
import com.servicos.entity.Propriedade;
import com.servicos.entity.Servicos;
import com.servicos.repositories.LaboratorioRepository;
import com.servicos.repositories.PropriedadeRepository;
import com.servicos.repositories.ServicosRepository;
import com.servicos.services.exception.ConstraintVException;
import com.servicos.services.exception.DataIntegrityException;
import com.servicos.services.exception.ObjectNFException;

@Service
public class ServicosService {
	@Autowired
	private ServicosRepository repo;
	@Autowired
	private LaboratorioRepository laboratorioRepo;
	@Autowired
	private PropriedadeRepository propriedadeRepo;

	public Servicos findById(Integer id) {
		Optional<Servicos> objServicos = repo.findById(id);

		return objServicos.orElseThrow(() -> new ObjectNFException("Serviço não encontrado! Id: " + id));
	}

	public List<Servicos> findAll() {
		List<Servicos> listServicos = repo.findAll();
		return listServicos;
	}

	public Servicos insert(Servicos obj) {
		obj.setId(null);
		try {
			repo.save(obj);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Serviço já cadastrado!");
		}
		return obj;
	}

	public Servicos update(Servicos obj) {
		findById(obj.getId());
		try {
			repo.save(obj);
		} catch (DataIntegrityViolationException e) {
			if (e.getMostSpecificCause().getMessage().contains("Unique")) {
				throw new ConstraintVException("Serviço já cadastrado!");
			}

		}
		return obj;
	}

	public void delete(Integer id) {
		findById(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma serviço que está em uso!");
		}
	}

	public Page<Servicos> findPage(String search, Integer page, Integer size, String orderBy, String direction) {
		Pageable pageRequest = PageRequest.of(page, size, Direction.valueOf(direction), orderBy);

		return repo.findByNomePagination(search, pageRequest);
	}

	public Servicos fromDTO(ServicosDTO objDTO) {
		Laboratorio laboratorio = laboratorioRepo.findById(objDTO.getLaboratorio().getId()).get();
		Propriedade propriedade = propriedadeRepo.findById(objDTO.getInfosPropriedade().getId()).get();

		return Servicos.builder().id(objDTO.getId()).nome(objDTO.getNome()).dataInicial(objDTO.getDataInicial())
				.dataFinal(objDTO.getDataFinal()).observacoes(objDTO.getObservacoes()).laboratorio(laboratorio)
				.propriedade(propriedade).build();
	}
}

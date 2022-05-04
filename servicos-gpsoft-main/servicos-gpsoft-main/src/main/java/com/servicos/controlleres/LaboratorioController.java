package com.servicos.controlleres;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.servicos.dto.LaboratorioDTO;
import com.servicos.entity.Laboratorio;
import com.servicos.services.LaboratorioService;

@RestController
@RequestMapping(value = "/Laboratorios")
public class LaboratorioController {

	@Autowired
	private LaboratorioService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Laboratorio>> findAll() {
		List<Laboratorio> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Laboratorio> findById(@PathVariable Integer id) {
		Laboratorio obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Laboratorio> insert(@Valid @RequestBody LaboratorioDTO objDto) {
		Laboratorio obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Laboratorio> update(@Valid @RequestBody LaboratorioDTO objDto, @PathVariable Integer id) {
		Laboratorio obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<LaboratorioDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "24") Integer size,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Laboratorio> list = service.findPage(search, page, size, orderBy, direction);
		Page<LaboratorioDTO> listDto = list.map(obj -> new LaboratorioDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
}

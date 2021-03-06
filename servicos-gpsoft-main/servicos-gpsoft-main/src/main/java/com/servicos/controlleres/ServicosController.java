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

import com.servicos.dto.ServicosDTO;
import com.servicos.entity.Servicos;
import com.servicos.services.ServicosService;

@RestController
@RequestMapping(value = "/Servicos")
public class ServicosController {

	@Autowired
	private ServicosService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Servicos>> findAll() {
		List<Servicos> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Servicos> findById(@PathVariable Integer id) {
		Servicos obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Servicos> insert(@Valid @RequestBody ServicosDTO objDto) {
		Servicos obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Servicos> update(@Valid @RequestBody ServicosDTO objDto, @PathVariable Integer id) {
		Servicos obj = service.fromDTO(objDto);
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
	public ResponseEntity<Page<Servicos>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "24") Integer size,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Servicos> list = service.findPage(search, page, size, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}

}

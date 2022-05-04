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

import com.servicos.dto.PropriedadeDTO;
import com.servicos.entity.Propriedade;
import com.servicos.services.PropriedadeService;

@RestController
@RequestMapping(value = "/Propriedades")
public class PropriedadeController {

	@Autowired
	private PropriedadeService service;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Propriedade>> findAll() {
		List<Propriedade> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Propriedade> findById(@PathVariable Integer id) {
		Propriedade obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Propriedade> insert(@Valid @RequestBody PropriedadeDTO objDto) {
		Propriedade obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Propriedade> update(@Valid @RequestBody PropriedadeDTO objDto, @PathVariable Integer id) {
		Propriedade obj = service.fromDTO(objDto);
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
	public ResponseEntity<Page<PropriedadeDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "size", defaultValue = "24") Integer size,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "search", defaultValue = "") String search,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Propriedade> list = service.findPage(search, page, size, orderBy, direction);
		Page<PropriedadeDTO> listDto = list.map(obj -> new PropriedadeDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
}

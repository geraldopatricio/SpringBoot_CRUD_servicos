package com.servicos.controllers;

import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.servicos.controlleres.PropriedadeController;
import com.servicos.dto.PropriedadeDTO;
import com.servicos.entity.Propriedade;
import com.servicos.repositories.PropriedadeRepositoryTest;
import com.servicos.services.PropriedadeService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = PropriedadeController.class)
@AutoConfigureMockMvc
public class PropriedadeControllerTest {

	static final String API = "/Propriedades";
	static final MediaType JSON = MediaType.APPLICATION_JSON;

	@Autowired
	MockMvc mvc;

	@MockBean
	PropriedadeService service;

	@Test
	public void shouldCreateAPropriedade() throws Exception {
		// scenario
		PropriedadeDTO dto = createPropriedadeDto();
		Propriedade propriedade = PropriedadeRepositoryTest.createPropriedade();
		propriedade.setId(1);

		Mockito.when(service.fromDTO(Mockito.any(PropriedadeDTO.class))).thenReturn(propriedade);
		Mockito.when(service.insert(Mockito.any(Propriedade.class))).thenReturn(propriedade);
		String json = new ObjectMapper().writeValueAsString(dto);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(JSON).contentType(JSON)
				.content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/Propriedades/1"));

	}

	@Test
	public void shouldUpdateAPropriedade() throws Exception {
		// scenario
		PropriedadeDTO dto = createPropriedadeDto();
		Propriedade propriedade = PropriedadeRepositoryTest.createPropriedade();
		propriedade.setId(1);

		Mockito.when(service.fromDTO(Mockito.any(PropriedadeDTO.class))).thenReturn(propriedade);
		Mockito.when(service.update(Mockito.any(Propriedade.class))).thenReturn(propriedade);
		String json = new ObjectMapper().writeValueAsString(dto);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API + "/1").accept(JSON).contentType(JSON)
				.content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());

	}

	@Test
	public void shouldDeleteAPropriedade() throws Exception {
		// scenario
		Mockito.doNothing().when(service).delete(1);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API + "/1").accept(JSON)
				.contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());

	}

	@Test
	public void shouldFindAllPropriedade() throws Exception {
		// scenario
		Propriedade propriedade = PropriedadeRepositoryTest.createPropriedade();
		propriedade.setId(1);
		List<Propriedade> list = Arrays.asList(propriedade);
		Mockito.when(service.findAll()).thenReturn(list);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API).accept(JSON).contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(asJsonString(list)));

	}

	@Test
	public void shouldFindByIdPropriedade() throws Exception {
		// scenario
		Propriedade propriedade = PropriedadeRepositoryTest.createPropriedade();
		propriedade.setId(1);

		Mockito.when(service.findById(1)).thenReturn(propriedade);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/1").accept(JSON).contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(asJsonString(propriedade)));

	}

	@Test
	public void shouldReturnPaginationPropriedade() throws Exception {
		// scenario
		Propriedade propriedade = PropriedadeRepositoryTest.createPropriedade();
		propriedade.setId(1);
		List<Propriedade> list = Arrays.asList(propriedade);

		Pageable pageable = PageRequest.of(0, 8);
		final Page<Propriedade> page = new PageImpl<>(list.subList(0, 1), pageable, 2);
		Mockito.when(service.findPage(any(String.class), any(Integer.class), any(Integer.class), any(String.class),
				any(String.class))).thenReturn(page);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/page").accept(JSON)
				.contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(asJsonString(page)));

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static PropriedadeDTO createPropriedadeDto() {
		PropriedadeDTO dto = new PropriedadeDTO();
		dto.setNome("propriedade1");
		dto.setCnpj("93.724.318/0001-04");
		return dto;

	}

}

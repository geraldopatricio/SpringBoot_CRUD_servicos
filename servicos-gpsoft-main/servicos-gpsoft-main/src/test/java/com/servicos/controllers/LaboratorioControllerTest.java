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
import com.servicos.controlleres.LaboratorioController;
import com.servicos.dto.LaboratorioDTO;
import com.servicos.entity.Laboratorio;
import com.servicos.repositories.LaboratorioRepositoryTest;
import com.servicos.services.LaboratorioService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = LaboratorioController.class)
@AutoConfigureMockMvc
public class LaboratorioControllerTest {

	static final String API = "/Laboratorios";
	static final MediaType JSON = MediaType.APPLICATION_JSON;

	@Autowired
	MockMvc mvc;

	@MockBean
	LaboratorioService service;

	@Test
	public void shouldCreateALaboratorio() throws Exception {
		// scenario
		LaboratorioDTO dto = createLaboratorioDto();
		Laboratorio laboratorio = LaboratorioRepositoryTest.createLaboratorio();
		laboratorio.setId(1);

		Mockito.when(service.fromDTO(Mockito.any(LaboratorioDTO.class))).thenReturn(laboratorio);
		Mockito.when(service.insert(Mockito.any(Laboratorio.class))).thenReturn(laboratorio);
		String json = new ObjectMapper().writeValueAsString(dto);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(JSON).contentType(JSON)
				.content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/Laboratorios/1"));

	}

	@Test
	public void shouldUpdateALaboratorio() throws Exception {
		// scenario
		LaboratorioDTO dto = createLaboratorioDto();
		Laboratorio laboratorio = LaboratorioRepositoryTest.createLaboratorio();
		laboratorio.setId(1);

		Mockito.when(service.fromDTO(Mockito.any(LaboratorioDTO.class))).thenReturn(laboratorio);
		Mockito.when(service.update(Mockito.any(Laboratorio.class))).thenReturn(laboratorio);
		String json = new ObjectMapper().writeValueAsString(dto);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API + "/1").accept(JSON).contentType(JSON)
				.content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());

	}

	@Test
	public void shouldDeleteALaboratorio() throws Exception {
		// scenario
		Mockito.doNothing().when(service).delete(1);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API + "/1").accept(JSON)
				.contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());

	}

	@Test
	public void shouldFindAllLaboratorio() throws Exception {
		// scenario
		Laboratorio laboratorio = LaboratorioRepositoryTest.createLaboratorio();
		laboratorio.setId(1);
		List<Laboratorio> list = Arrays.asList(laboratorio);
		Mockito.when(service.findAll()).thenReturn(list);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API).accept(JSON).contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(asJsonString(list)));

	}

	@Test
	public void shouldFindByIdLaboratorio() throws Exception {
		// scenario
		Laboratorio laboratorio = LaboratorioRepositoryTest.createLaboratorio();
		laboratorio.setId(1);

		Mockito.when(service.findById(1)).thenReturn(laboratorio);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/1").accept(JSON).contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(asJsonString(laboratorio)));

	}

	@Test
	public void shouldReturnPaginationLaboratorio() throws Exception {
		// scenario
		Laboratorio laboratorio = LaboratorioRepositoryTest.createLaboratorio();
		laboratorio.setId(1);
		List<Laboratorio> list = Arrays.asList(laboratorio);

		Pageable pageable = PageRequest.of(0, 8);
		final Page<Laboratorio> page = new PageImpl<>(list.subList(0, 1), pageable, 2);
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

	public static LaboratorioDTO createLaboratorioDto() {
		LaboratorioDTO dto = new LaboratorioDTO();
		dto.setNome("laboratorio1");
		return dto;

	}

}

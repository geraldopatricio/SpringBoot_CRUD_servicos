package com.servicos.controllers;

import static org.mockito.ArgumentMatchers.any;

import java.util.Arrays;
import java.util.Date;
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
import com.servicos.controlleres.ServicosController;
import com.servicos.dto.InfosPropriedadeDTO;
import com.servicos.dto.LaboratorioDTO;
import com.servicos.dto.ServicosDTO;
import com.servicos.entity.Servicos;
import com.servicos.repositories.ServicoRepositoryTest;
import com.servicos.services.ServicosService;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ServicosController.class)
@AutoConfigureMockMvc
public class ServicoControllerTest {

	static final String API = "/Servicos";
	static final MediaType JSON = MediaType.APPLICATION_JSON;

	@Autowired
	MockMvc mvc;

	@MockBean
	ServicosService service;

	@Test
	public void shouldCreateAServicos() throws Exception {
		// scenario
		ServicosDTO dto = createServicosDto();
		Servicos servicos = ServicoRepositoryTest.createServicos();
		servicos.setId(1);

		Mockito.when(service.fromDTO(Mockito.any(ServicosDTO.class))).thenReturn(servicos);
		Mockito.when(service.insert(Mockito.any(Servicos.class))).thenReturn(servicos);
		String json = new ObjectMapper().writeValueAsString(dto);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API).accept(JSON).contentType(JSON)
				.content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.redirectedUrl("http://localhost/Servicos/1"));

	}

	@Test
	public void shouldUpdateAServicos() throws Exception {
		// scenario
		ServicosDTO dto = createServicosDto();
		Servicos servicos = ServicoRepositoryTest.createServicos();
		servicos.setId(1);

		Mockito.when(service.fromDTO(Mockito.any(ServicosDTO.class))).thenReturn(servicos);
		Mockito.when(service.update(Mockito.any(Servicos.class))).thenReturn(servicos);
		String json = new ObjectMapper().writeValueAsString(dto);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.put(API + "/1").accept(JSON).contentType(JSON)
				.content(json);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());

	}

	@Test
	public void shouldDeleteAServicos() throws Exception {
		// scenario
		Mockito.doNothing().when(service).delete(1);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.delete(API + "/1").accept(JSON)
				.contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isNoContent());

	}

	@Test
	public void shouldFindAllServicos() throws Exception {
		// scenario
		Servicos servicos = ServicoRepositoryTest.createServicos();
		servicos.setId(1);
		List<Servicos> list = Arrays.asList(servicos);
		Mockito.when(service.findAll()).thenReturn(list);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API).accept(JSON).contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(asJsonString(list)));

	}

	@Test
	public void shouldFindByIdServicos() throws Exception {
		// scenario
		Servicos servicos = ServicoRepositoryTest.createServicos();
		servicos.setId(1);

		Mockito.when(service.findById(1)).thenReturn(servicos);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/1").accept(JSON).contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json(asJsonString(servicos)));

	}

	@Test
	public void shouldReturnPaginationServicos() throws Exception {
		// scenario
		Servicos servicos = ServicoRepositoryTest.createServicos();
		servicos.setId(1);
		List<Servicos> list = Arrays.asList(servicos);

		Pageable pageable = PageRequest.of(0, 8);
		final Page<Servicos> page = new PageImpl<>(list.subList(0, 1), pageable, 2);
		Mockito.when(service.findPage(any(String.class), any(Integer.class), any(Integer.class), any(String.class),
				any(String.class))).thenReturn(page);

		// execution and verification
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API + "/page").accept(JSON)
				.contentType(JSON);

		mvc.perform(request).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(JSON));

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static ServicosDTO createServicosDto() {
		InfosPropriedadeDTO infoPropriedade = new InfosPropriedadeDTO();
		LaboratorioDTO laboratorio = new LaboratorioDTO();

		ServicosDTO dto = new ServicosDTO();
		dto.setNome("servicos1");
		dto.setDataInicial(new Date("01/04/2022 12:50:00"));
		dto.setDataFinal(new Date("02/04/2022 12:50:00"));
		dto.setInfosPropriedade(infoPropriedade);
		dto.setLaboratorio(laboratorio);
		dto.setObservacoes("nova observacao");
		return dto;

	}

}

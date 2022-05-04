package com.servicos.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.servicos.controllers.ServicoControllerTest;
import com.servicos.dto.ServicosDTO;
import com.servicos.entity.Laboratorio;
import com.servicos.entity.Propriedade;
import com.servicos.entity.Servicos;
import com.servicos.repositories.LaboratorioRepository;
import com.servicos.repositories.PropriedadeRepository;
import com.servicos.repositories.ServicoRepositoryTest;
import com.servicos.repositories.ServicosRepository;
import com.servicos.services.exception.ConstraintVException;
import com.servicos.services.exception.DataIntegrityException;
import com.servicos.services.exception.ObjectNFException;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ServicosServiceTest {

	@Mock
	ServicosRepository repository;

	@Mock
	PropriedadeRepository propriedadeRepository;

	@Mock
	LaboratorioRepository laboratorioRepository;

	@Mock
	HttpServletRequest request;

	@InjectMocks
	ServicosService service;

	@Test
	public void shouldSaveServicos() {
		// scenario
		Servicos servicosToSave = ServicoRepositoryTest.createServicos();

		Servicos servicosSaved = ServicoRepositoryTest.createServicos();
		servicosSaved.setId(1);
		when(repository.save(servicosToSave)).thenReturn(servicosSaved);

		// action
		Servicos servicos = service.insert(servicosToSave);

		// verification
		verify(repository, times(1)).save(servicosToSave);

	}

	@Test
	public void shouldThrowErrorDataIntegrityExceptionWhenTryingtoSaveServicosThatHasntBeenSaved() {
		// scenario
		Servicos servicosToSave = ServicoRepositoryTest.createServicos();

		doThrow(DataIntegrityViolationException.class).when(repository).save(servicosToSave);

		// execution
		DataIntegrityException result = catchThrowableOfType(() -> service.insert(servicosToSave),
				DataIntegrityException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Serviço já cadastrado!");

	}

	@Test
	public void shouldUpdateServicos() {
		// scenario
		Servicos servicosSaved = ServicoRepositoryTest.createServicos();
		servicosSaved.setId(1);

		when(repository.findById(servicosSaved.getId())).thenReturn(Optional.of(servicosSaved));
		when(repository.save(servicosSaved)).thenReturn(servicosSaved);

		// execution
		service.update(servicosSaved);

		// verification
		verify(repository, times(1)).save(servicosSaved);

	}

	@Test
	public void shouldThrowErrorWhenTryingtoUpdateAServicosThatHasntBeenSaved() {
		// scenario
		Servicos servicosToSave = ServicoRepositoryTest.createServicos();
		servicosToSave.setId(1);

		// execution
		ObjectNFException result = catchThrowableOfType(() -> service.update(servicosToSave), ObjectNFException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Serviço não encontrado! Id: 1");

	}

	@Test
	public void shouldThrowErrorDataIntegrityExceptionWhenTryingtoUpdateServicosThatHasntBeenSaved() {
		// scenario
		Servicos servicosToSave = ServicoRepositoryTest.createServicos();
		servicosToSave.setId(1);

		when(repository.findById(servicosToSave.getId())).thenReturn(Optional.of(servicosToSave));
		doThrow(new DataIntegrityViolationException("Unique")).when(repository).save(servicosToSave);

		// execution
		ConstraintVException result = catchThrowableOfType(() -> service.update(servicosToSave),
				ConstraintVException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Serviço já cadastrado!");

	}

	@Test
	public void shouldDeleteServicos() {
		// scenario
		Servicos servicos = ServicoRepositoryTest.createServicos();
		servicos.setId(1);
		when(repository.findById(servicos.getId())).thenReturn(Optional.of(servicos));
		doNothing().when(repository).deleteById(servicos.getId());

		// execution
		service.delete(servicos.getId());

		// verification
		verify(repository).deleteById(servicos.getId());
	}

	@Test
	public void shouldThrowErrorWhenTryingtoDeleteAPurchase() {
		// scenario
		Servicos servicosToDelete = ServicoRepositoryTest.createServicos();
		servicosToDelete.setId(1);

		when(repository.findById(servicosToDelete.getId())).thenReturn(Optional.of(servicosToDelete));
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(1);

		// execution
		DataIntegrityException result = catchThrowableOfType(() -> service.delete(1), DataIntegrityException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Não é possível excluir uma serviço que está em uso!");

	}

	@Test
	public void shouldReturnAllServicos() {
		// scenario
		Servicos servicos = ServicoRepositoryTest.createServicos();
		servicos.setId(1);

		List<Servicos> list = Arrays.asList(servicos);
		when(repository.findAll()).thenReturn(list);

		// execution
		List<Servicos> result = service.findAll();

		// verification
		assertThat(result).isNotEmpty().hasSize(1).contains(servicos);

	}

	@Test
	public void shouldReturnAllServicosPagination() {
		// scenario
		Servicos servicos = ServicoRepositoryTest.createServicos();
		servicos.setId(1);

		List<Servicos> list = Arrays.asList(servicos);
		Pageable pageable = PageRequest.of(0, 8);
		final Page<Servicos> page = new PageImpl<>(list.subList(0, 1), pageable, 2);
		when(repository.findByNomePagination(any(String.class), any(PageRequest.class))).thenReturn(page);

		// execution
		Page<Servicos> result = service.findPage("", 0, 24, "id", "ASC");

		// verification
		assertThat(result).isNotNull();

	}

	@Test
	public void shouldReturnServicosReceiveServicosDTO() {
		// scenario
		ServicosDTO dto = ServicoControllerTest.createServicosDto();
		Propriedade propriedade = Propriedade.builder().nome("propriedade").build();
		Laboratorio lab = Laboratorio.builder().nome("lab").build();

		when(propriedadeRepository.findById(dto.getInfosPropriedade().getId())).thenReturn(Optional.of(propriedade));
		when(laboratorioRepository.findById(dto.getLaboratorio().getId())).thenReturn(Optional.of(lab));

		// execution
		Servicos result = service.fromDTO(dto);

		// verification
		assertThat(result).isInstanceOf(Servicos.class);

	}

}

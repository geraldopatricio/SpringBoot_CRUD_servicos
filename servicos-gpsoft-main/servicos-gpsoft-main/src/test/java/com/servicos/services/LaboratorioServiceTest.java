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

import com.servicos.controllers.LaboratorioControllerTest;
import com.servicos.dto.LaboratorioDTO;
import com.servicos.entity.Laboratorio;
import com.servicos.repositories.LaboratorioRepository;
import com.servicos.repositories.LaboratorioRepositoryTest;
import com.servicos.services.exception.ConstraintVException;
import com.servicos.services.exception.DataIntegrityException;
import com.servicos.services.exception.ObjectNFException;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class LaboratorioServiceTest {

	@Mock
	LaboratorioRepository repository;

	@Mock
	HttpServletRequest request;

	@InjectMocks
	LaboratorioService service;

	@Test
	public void shouldSaveLaboratorio() {
		// scenario
		Laboratorio laboratorioToSave = LaboratorioRepositoryTest.createLaboratorio();

		Laboratorio laboratorioSaved = LaboratorioRepositoryTest.createLaboratorio();
		laboratorioSaved.setId(1);
		when(repository.save(laboratorioToSave)).thenReturn(laboratorioSaved);

		// action
		Laboratorio laboratorio = service.insert(laboratorioToSave);

		// verification
		verify(repository, times(1)).save(laboratorioToSave);

	}

	@Test
	public void shouldThrowErrorDataIntegrityExceptionWhenTryingtoSaveALaboratorioThatHasntBeenSaved() {
		// scenario
		Laboratorio laboratorioToSave = LaboratorioRepositoryTest.createLaboratorio();

		doThrow(DataIntegrityViolationException.class).when(repository).save(laboratorioToSave);

		// execution
		DataIntegrityException result = catchThrowableOfType(() -> service.insert(laboratorioToSave),
				DataIntegrityException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Laboratório já cadastrado!");

	}

	@Test
	public void shouldUpdateLaboratorio() {
		// scenario
		Laboratorio laboratorioSaved = LaboratorioRepositoryTest.createLaboratorio();
		laboratorioSaved.setId(1);

		when(repository.findById(laboratorioSaved.getId())).thenReturn(Optional.of(laboratorioSaved));
		when(repository.save(laboratorioSaved)).thenReturn(laboratorioSaved);

		// execution
		service.update(laboratorioSaved);

		// verification
		verify(repository, times(1)).save(laboratorioSaved);

	}

	@Test
	public void shouldThrowErrorWhenTryingtoUpdateALaboratorioThatHasntBeenSaved() {
		// scenario
		Laboratorio laboratorioToSave = LaboratorioRepositoryTest.createLaboratorio();
		laboratorioToSave.setId(1);

		// execution
		ObjectNFException result = catchThrowableOfType(() -> service.update(laboratorioToSave),
				ObjectNFException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Laboratório não encontrado! Id: 1");

	}

	@Test
	public void shouldThrowErrorDataIntegrityExceptionWhenTryingtoUpdateALaboratorioThatHasntBeenSaved() {
		// scenario
		Laboratorio laboratorioToSave = LaboratorioRepositoryTest.createLaboratorio();
		laboratorioToSave.setId(1);

		when(repository.findById(laboratorioToSave.getId())).thenReturn(Optional.of(laboratorioToSave));
		doThrow(new DataIntegrityViolationException("Unique")).when(repository).save(laboratorioToSave);

		// execution
		ConstraintVException result = catchThrowableOfType(() -> service.update(laboratorioToSave),
				ConstraintVException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Laboratório já cadastrado!");

	}

	@Test
	public void shouldDeleteLaboratorio() {
		// scenario
		Laboratorio laboratorio = LaboratorioRepositoryTest.createLaboratorio();
		laboratorio.setId(1);
		when(repository.findById(laboratorio.getId())).thenReturn(Optional.of(laboratorio));
		doNothing().when(repository).deleteById(laboratorio.getId());

		// execution
		service.delete(laboratorio.getId());

		// verification
		verify(repository).deleteById(laboratorio.getId());
	}

	@Test
	public void shouldThrowErrorWhenTryingtoDeleteAPurchase() {
		// scenario
		Laboratorio laboratorioToDelete = LaboratorioRepositoryTest.createLaboratorio();
		laboratorioToDelete.setId(1);

		when(repository.findById(laboratorioToDelete.getId())).thenReturn(Optional.of(laboratorioToDelete));
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(1);

		// execution
		DataIntegrityException result = catchThrowableOfType(() -> service.delete(1), DataIntegrityException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Não é possível excluir uma Laboratório que está em uso!");

	}

	@Test
	public void shouldReturnAllLaboratorio() {
		// scenario
		Laboratorio laboratorio = LaboratorioRepositoryTest.createLaboratorio();
		laboratorio.setId(1);

		List<Laboratorio> list = Arrays.asList(laboratorio);
		when(repository.findAll()).thenReturn(list);

		// execution
		List<Laboratorio> result = service.findAll();

		// verification
		assertThat(result).isNotEmpty().hasSize(1).contains(laboratorio);

	}

	@Test
	public void shouldReturnAllLaboratorioPagination() {
		// scenario
		Laboratorio laboratorio = LaboratorioRepositoryTest.createLaboratorio();
		laboratorio.setId(1);

		List<Laboratorio> list = Arrays.asList(laboratorio);
		Pageable pageable = PageRequest.of(0, 8);
		final Page<Laboratorio> page = new PageImpl<>(list.subList(0, 1), pageable, 2);
		when(repository.findByNomePagination(any(String.class), any(PageRequest.class))).thenReturn(page);

		// execution
		Page<Laboratorio> result = service.findPage("", 0, 24, "id", "ASC");

		// verification
		assertThat(result).isNotNull();

	}

	@Test
	public void shouldReturnLaboratorioReceiveLaboratorioDTO() {
		// scenario
		LaboratorioDTO dto = LaboratorioControllerTest.createLaboratorioDto();

		// execution
		Laboratorio result = service.fromDTO(dto);

		// verification
		assertThat(result).isInstanceOf(Laboratorio.class);

	}

}

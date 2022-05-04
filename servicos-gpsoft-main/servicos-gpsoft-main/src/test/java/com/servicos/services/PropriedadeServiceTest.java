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

import com.servicos.controllers.PropriedadeControllerTest;
import com.servicos.dto.PropriedadeDTO;
import com.servicos.entity.Propriedade;
import com.servicos.repositories.PropriedadeRepository;
import com.servicos.repositories.PropriedadeRepositoryTest;
import com.servicos.services.exception.ConstraintVException;
import com.servicos.services.exception.DataIntegrityException;
import com.servicos.services.exception.ObjectNFException;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class PropriedadeServiceTest {

	@Mock
	PropriedadeRepository repository;

	@Mock
	HttpServletRequest request;

	@InjectMocks
	PropriedadeService service;

	@Test
	public void shouldSavePropriedade() {
		// scenario
		Propriedade propriedadeToSave = PropriedadeRepositoryTest.createPropriedade();

		Propriedade propriedadeSaved = PropriedadeRepositoryTest.createPropriedade();
		propriedadeSaved.setId(1);
		when(repository.save(propriedadeToSave)).thenReturn(propriedadeSaved);

		// action
		Propriedade propriedade = service.insert(propriedadeToSave);

		// verification
		verify(repository, times(1)).save(propriedadeToSave);

	}

	@Test
	public void shouldThrowErrorDataIntegrityExceptionWhenTryingtoSavePropriedadeThatHasntBeenSaved() {
		// scenario
		Propriedade propriedadeToSave = PropriedadeRepositoryTest.createPropriedade();

		doThrow(DataIntegrityViolationException.class).when(repository).save(propriedadeToSave);

		// execution
		DataIntegrityException result = catchThrowableOfType(() -> service.insert(propriedadeToSave),
				DataIntegrityException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Propriedade já cadastrada!");

	}

	@Test
	public void shouldUpdatePropriedade() {
		// scenario
		Propriedade propriedadeSaved = PropriedadeRepositoryTest.createPropriedade();
		propriedadeSaved.setId(1);

		when(repository.findById(propriedadeSaved.getId())).thenReturn(Optional.of(propriedadeSaved));
		when(repository.save(propriedadeSaved)).thenReturn(propriedadeSaved);

		// execution
		service.update(propriedadeSaved);

		// verification
		verify(repository, times(1)).save(propriedadeSaved);

	}

	@Test
	public void shouldThrowErrorWhenTryingtoUpdateAPropriedadeThatHasntBeenSaved() {
		// scenario
		Propriedade propriedadeToSave = PropriedadeRepositoryTest.createPropriedade();
		propriedadeToSave.setId(1);

		// execution
		ObjectNFException result = catchThrowableOfType(() -> service.update(propriedadeToSave),
				ObjectNFException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Propriedade não encontrado! Id: 1");

	}

	@Test
	public void shouldThrowErrorDataIntegrityExceptionWhenTryingtoUpdatePropriedadeThatHasntBeenSaved() {
		// scenario
		Propriedade propriedadeToSave = PropriedadeRepositoryTest.createPropriedade();
		propriedadeToSave.setId(1);

		when(repository.findById(propriedadeToSave.getId())).thenReturn(Optional.of(propriedadeToSave));
		doThrow(new DataIntegrityViolationException("Unique")).when(repository).save(propriedadeToSave);

		// execution
		ConstraintVException result = catchThrowableOfType(() -> service.update(propriedadeToSave),
				ConstraintVException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Propriedade já cadastrada!");

	}

	@Test
	public void shouldDeletePropriedade() {
		// scenario
		Propriedade propriedade = PropriedadeRepositoryTest.createPropriedade();
		propriedade.setId(1);
		when(repository.findById(propriedade.getId())).thenReturn(Optional.of(propriedade));
		doNothing().when(repository).deleteById(propriedade.getId());

		// execution
		service.delete(propriedade.getId());

		// verification
		verify(repository).deleteById(propriedade.getId());
	}

	@Test
	public void shouldThrowErrorWhenTryingtoDeleteAPurchase() {
		// scenario
		Propriedade propriedadeToDelete = PropriedadeRepositoryTest.createPropriedade();
		propriedadeToDelete.setId(1);

		when(repository.findById(propriedadeToDelete.getId())).thenReturn(Optional.of(propriedadeToDelete));
		doThrow(DataIntegrityViolationException.class).when(repository).deleteById(1);

		// execution
		DataIntegrityException result = catchThrowableOfType(() -> service.delete(1), DataIntegrityException.class);

		// verification
		assertThat(result.getMessage()).isEqualTo("Não é possível excluir uma propriedade que está em uso!");

	}

	@Test
	public void shouldReturnAllPropriedade() {
		// scenario
		Propriedade propriedade = PropriedadeRepositoryTest.createPropriedade();
		propriedade.setId(1);

		List<Propriedade> list = Arrays.asList(propriedade);
		when(repository.findAll()).thenReturn(list);

		// execution
		List<Propriedade> result = service.findAll();

		// verification
		assertThat(result).isNotEmpty().hasSize(1).contains(propriedade);

	}

	@Test
	public void shouldReturnAllPropriedadePagination() {
		// scenario
		Propriedade propriedade = PropriedadeRepositoryTest.createPropriedade();
		propriedade.setId(1);

		List<Propriedade> list = Arrays.asList(propriedade);
		Pageable pageable = PageRequest.of(0, 8);
		final Page<Propriedade> page = new PageImpl<>(list.subList(0, 1), pageable, 2);
		when(repository.findByNomePagination(any(String.class), any(PageRequest.class))).thenReturn(page);

		// execution
		Page<Propriedade> result = service.findPage("", 0, 24, "id", "ASC");

		// verification
		assertThat(result).isNotNull();

	}

	@Test
	public void shouldReturnPropriedadeReceivePropriedadeDTO() {
		// scenario
		PropriedadeDTO dto = PropriedadeControllerTest.createPropriedadeDto();

		// execution
		Propriedade result = service.fromDTO(dto);

		// verification
		assertThat(result).isInstanceOf(Propriedade.class);

	}

}

package com.servicos.repositories;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.servicos.entity.Laboratorio;





@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class LaboratorioRepositoryTest {
	
	@Autowired
	LaboratorioRepository repository;
	
	@Autowired
	TestEntityManager entityManager;	
	

	
	@Test
	public void shouldSaveALaboratorio() {
		//scenario
		Laboratorio laboratorio = createLaboratorio();
		
		//action
		laboratorio = repository.save(laboratorio);
		
		//verification
		assertThat(laboratorio.getId()).isNotNull();
	}
	
	@Test
	public void shouldDeleteALaboratorio() {
		Laboratorio laboratorio = createandPersistLaboratorio();
		
		laboratorio = entityManager.find(Laboratorio.class, laboratorio.getId());
		
		repository.delete(laboratorio);
		
		Laboratorio laboratorioNonExist = entityManager.find(Laboratorio.class, laboratorio.getId());
		assertThat(laboratorioNonExist).isNull();
	}
	
	@Test
	public void shouldUpdateALaboratorio() {
		Laboratorio laboratorio = createandPersistLaboratorio();
		laboratorio.setId(1);
		
		
		repository.save(laboratorio);
		
		Laboratorio laboratorioUpdate = entityManager.find(Laboratorio.class, laboratorio.getId());

		
	}
	
	@Test
	public void shouldFindLaboratorioById() {
		Laboratorio laboratorio = createandPersistLaboratorio();
		
		Optional<Laboratorio> laboratorioFind = repository.findById(laboratorio.getId());
		
		assertThat(laboratorioFind.isPresent()).isTrue();
	}
	
	
	private Laboratorio createandPersistLaboratorio() {
		Laboratorio laboratorio = createLaboratorio();
		entityManager.persist(laboratorio);
		return laboratorio;
	}
	

	public static Laboratorio createLaboratorio(){			
		return Laboratorio.builder().nome("laboratorio1").build();
	}
}

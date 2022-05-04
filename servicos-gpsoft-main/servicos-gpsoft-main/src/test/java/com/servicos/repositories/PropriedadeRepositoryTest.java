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

import com.servicos.entity.Propriedade;





@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class PropriedadeRepositoryTest {
	
	@Autowired
	PropriedadeRepository repository;
	
	@Autowired
	TestEntityManager entityManager;	
	

	
	@Test
	public void shouldSaveAPropriedade() {
		//scenario
		Propriedade propriedade = createPropriedade();
		
		//action
		propriedade = repository.save(propriedade);
		
		//verification
		assertThat(propriedade.getId()).isNotNull();
	}
	
	@Test
	public void shouldDeleteAPropriedade() {
		Propriedade propriedade = createandPersistPropriedade();
		
		propriedade = entityManager.find(Propriedade.class, propriedade.getId());
		
		repository.delete(propriedade);
		
		Propriedade propriedadeNonExist = entityManager.find(Propriedade.class, propriedade.getId());
		assertThat(propriedadeNonExist).isNull();
	}
	
	@Test
	public void shouldUpdateAPropriedade() {
		Propriedade propriedade = createandPersistPropriedade();
		propriedade.setId(1);
		propriedade.setNome("teste");
		
		repository.save(propriedade);
		
		Propriedade propriedadeUpdate = entityManager.find(Propriedade.class, propriedade.getId());
		
	

		
	}
	
	@Test
	public void shouldFindPropriedadeById() {
		Propriedade propriedade = createandPersistPropriedade();
		
		Optional<Propriedade> propriedadeFind = repository.findById(propriedade.getId());
		
		assertThat(propriedadeFind.isPresent()).isTrue();
	}
	
	
	private Propriedade createandPersistPropriedade() {
		Propriedade propriedade = createPropriedade();
		entityManager.persist(propriedade);
		return propriedade;
	}
	

	public static Propriedade createPropriedade(){			
		return Propriedade.builder().nome("Propriedade1").cnpj("93.724.318/0001-04").build();
	}
}

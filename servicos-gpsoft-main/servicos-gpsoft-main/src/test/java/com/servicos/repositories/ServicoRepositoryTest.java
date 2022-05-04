package com.servicos.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
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

import com.servicos.dto.ServicosDTO;
import com.servicos.entity.Laboratorio;
import com.servicos.entity.Propriedade;
import com.servicos.entity.Servicos;





@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ServicoRepositoryTest {
	
	@Autowired
	ServicosRepository repository;
	
	@Autowired
	TestEntityManager entityManager;	
	

	
	@Test
	public void shouldSaveAServicos() {
		//scenario
		Servicos servicos = createServicos();
		
		//action
		servicos = repository.save(servicos);
		
		//verification
		assertThat(servicos.getId()).isNotNull();
	}
	
	@Test
	public void shouldDeleteAServicos() {
		Servicos servicos = createandPersistServicos();
		
		servicos = entityManager.find(Servicos.class, servicos.getId());
		
		repository.delete(servicos);
		
		Servicos servicosNonExist = entityManager.find(Servicos.class, servicos.getId());
		assertThat(servicosNonExist).isNull();
	}
	
	@Test
	public void shouldUpdateAServicos() {
		Servicos servicos = createandPersistServicos();
		servicos.setId(1);
		
		
		repository.save(servicos);
		
		Servicos servicosUpdate = entityManager.find(Servicos.class, servicos.getId());

		
	}
	
	@Test
	public void shouldFindServicosById() {
		Servicos servicos = createandPersistServicos();
		
		Optional<Servicos> servicosFind = repository.findById(servicos.getId());
		
		assertThat(servicosFind.isPresent()).isTrue();
	}
	
	
	private Servicos createandPersistServicos() {
		Servicos servicos = createServicos();
		entityManager.persist(servicos);
		return servicos;
	}
	

	public static Servicos createServicos(){			
		Laboratorio laboratorio = Laboratorio.builder().nome("labteste").build();
		Propriedade propriedade = Propriedade.builder().nome("teste").cnpj("93.724.318/0001-04").build();
		
		return Servicos.builder().nome("Servicos1").dataInicial(new Date("01/04/2022 12:50:00")).dataFinal(new Date("02/04/2022 12:50:00"))
				.observacoes("teste").laboratorio(laboratorio).propriedade(propriedade).build();
	}
}

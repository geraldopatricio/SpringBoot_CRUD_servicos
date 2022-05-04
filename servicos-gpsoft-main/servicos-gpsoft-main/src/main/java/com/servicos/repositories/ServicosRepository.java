package com.servicos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.servicos.entity.Servicos;

@Repository
public interface ServicosRepository extends JpaRepository<Servicos, Integer> {

	@Query("SELECT t FROM Servicos t WHERE " + "LOWER(t.nome) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
	Page<Servicos> findByNomePagination(@Param("searchTerm") String searchTerm, Pageable pageRequest);
}

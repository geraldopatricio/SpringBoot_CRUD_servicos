package com.servicos.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.servicos.entity.Propriedade;


@Repository
public interface PropriedadeRepository extends JpaRepository<Propriedade,Integer>{

	@Query("SELECT t FROM Propriedade t WHERE " +
            "LOWER(t.nome) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    Page<Propriedade> findByNomePagination(@Param("searchTerm") String searchTerm, 
                                Pageable pageRequest);

}

package com.os.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.os.domain.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer>{

	@Query("SELECT tecnico FROM Tecnico tecnico WHERE tecnico.cpf =:cpf")
	Tecnico findByCpf(@Param("cpf") String cpf);

}
 
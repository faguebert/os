package com.os.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.os.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	@Query("SELECT cliente FROM Cliente cliente WHERE cliente.cpf =:cpf")
	Cliente findByCpf(@Param("cpf") String cpf);
}

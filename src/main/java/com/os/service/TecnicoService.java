package com.os.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.os.domain.Tecnico;
import com.os.dtos.TecnicoDTO;
import com.os.repository.TecnicoRepository;
import com.os.service.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> tecnico = tecnicoRepository.findById(id);
		
		return tecnico.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Tecnico.class.getName()));
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();
	}
	
	public Tecnico create(TecnicoDTO tecnicoDto) {
		return tecnicoRepository.save(new Tecnico(null, tecnicoDto.getNome(), tecnicoDto.getCpf(), tecnicoDto.getTelefone()));
	}

	public void delete(Integer id) {
		tecnicoRepository.deleteById(id);
	}

}

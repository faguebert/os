package com.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.os.domain.Tecnico;
import com.os.dtos.TecnicoDTO;
import com.os.repository.TecnicoRepository;
import com.os.resources.exceptions.DataIntegratyViolationException;
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
		if(findByCPF(tecnicoDto) != null){
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		return tecnicoRepository.save(new Tecnico(null, tecnicoDto.getNome(), tecnicoDto.getCpf(), tecnicoDto.getTelefone()));
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDTO tecnicoDto) {
		Tecnico tecnicoAtualizado = findById(id);
		
		if(findByCPF(tecnicoDto) != null && findByCPF(tecnicoDto).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		tecnicoAtualizado.setNome(tecnicoDto.getNome());
		tecnicoAtualizado.setCpf(tecnicoDto.getCpf());
		tecnicoAtualizado.setTelefone(tecnicoDto.getTelefone());
		
		return tecnicoRepository.save(tecnicoAtualizado);
	}

	
	private Tecnico findByCPF(TecnicoDTO tecnicoDto) {
		Tecnico tecnico = tecnicoRepository.findByCpf(tecnicoDto.getCpf());
		if(tecnico != null) {
			return tecnico;
		}
		return null;
	}

	public void delete(Integer id) {
		tecnicoRepository.deleteById(id);
	}

	
}

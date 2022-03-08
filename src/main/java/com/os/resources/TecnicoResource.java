package com.os.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.os.domain.Tecnico;
import com.os.dtos.TecnicoDTO;
import com.os.service.TecnicoService;

@RestController
@RequestMapping(value = "/tecnicos")
public class TecnicoResource {
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id){
		TecnicoDTO tecnicoDto = new TecnicoDTO(tecnicoService.findById(id));
		
		return ResponseEntity.ok().body(tecnicoDto);
	}
	
	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll(){

		List<TecnicoDTO> tecnicoListDto = tecnicoService.findAll().stream().map(
				tecnico -> new TecnicoDTO(tecnico)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(tecnicoListDto);
	}
	
	@PostMapping
	public ResponseEntity<Tecnico> create(@Valid @RequestBody TecnicoDTO tecnicoDto){
		Tecnico tecnico = tecnicoService.create(tecnicoDto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(tecnico.getId()).toUri();
		
		return ResponseEntity.created(uri).body(tecnico);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		tecnicoService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO tecnicoDto){
		TecnicoDTO novoTecnicoDto = new TecnicoDTO(tecnicoService.update(id, tecnicoDto));
		return ResponseEntity.ok().body(novoTecnicoDto);
	}
	
}

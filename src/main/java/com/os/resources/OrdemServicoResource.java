package com.os.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.os.dtos.OrdemServicoDTO;
import com.os.service.OrdemServicoService;

@RestController
@RequestMapping(value = "/ordemServico")
public class OrdemServicoResource {

	@Autowired
	private OrdemServicoService ordemServicoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrdemServicoDTO> findById(@PathVariable Integer id){
		OrdemServicoDTO ordemServicoDTO = new OrdemServicoDTO(ordemServicoService.findById(id));
		return ResponseEntity.ok().body(ordemServicoDTO);
	}
	
	@GetMapping
	public ResponseEntity<List<OrdemServicoDTO>> findAll(){
		List<OrdemServicoDTO> ordemServicoList = ordemServicoService.findAll().stream()
				.map(ordemServico -> new OrdemServicoDTO(ordemServico)).collect(Collectors.toList());
		return ResponseEntity.ok().body(ordemServicoList);
	}
	
	@PostMapping
	public ResponseEntity<OrdemServicoDTO> create(@Valid @RequestBody OrdemServicoDTO ordemServicoDTO){
		ordemServicoDTO = new OrdemServicoDTO(ordemServicoService.create(ordemServicoDTO));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(ordemServicoDTO.getId()).toUri();
		return ResponseEntity.created(uri).body(ordemServicoDTO);
	}
	
	@PutMapping
	public ResponseEntity<OrdemServicoDTO> update(@Valid @RequestBody OrdemServicoDTO ordemServicoDTO){
		ordemServicoDTO = new OrdemServicoDTO(ordemServicoService.update(ordemServicoDTO));
		return ResponseEntity.ok().body(ordemServicoDTO);
	}
}

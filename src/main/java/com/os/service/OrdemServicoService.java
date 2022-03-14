package com.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.os.domain.Cliente;
import com.os.domain.OrdemServico;
import com.os.domain.Tecnico;
import com.os.domain.enums.Prioridade;
import com.os.domain.enums.Status;
import com.os.dtos.OrdemServicoDTO;
import com.os.repository.OrdemServicoRepository;
import com.os.service.exceptions.ObjectNotFoundException;

@Service
public class OrdemServicoService {

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private TecnicoService tecnicoService;
	
	@Autowired
	private ClienteService clienteService;

	public OrdemServico findById(Integer id) {
		Optional<OrdemServico> ordemServico = ordemServicoRepository.findById(id);
		return ordemServico.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + "Tipo: " + OrdemServico.class.getName()));
	}
	
	public List<OrdemServico> findAll(){
		return ordemServicoRepository.findAll();
	}

	public OrdemServico create(@Valid OrdemServicoDTO ordemServicoDTO) {
		return fromDTO(ordemServicoDTO);
	}
	
	public OrdemServico update(@Valid OrdemServicoDTO ordemServicoDTO) {
		findById(ordemServicoDTO.getId());
		return fromDTO(ordemServicoDTO);
	}
	
	private OrdemServico fromDTO(OrdemServicoDTO ordemServicoDTO) {
		OrdemServico ordemServico = new OrdemServico();
		ordemServico.setId(ordemServicoDTO.getId());
		ordemServico.setObservacoes(ordemServicoDTO.getObservacoes());
		ordemServico.setPrioridade(Prioridade.toEnum(ordemServicoDTO.getPrioridade()));
		ordemServico.setStatus(Status.toEnum(ordemServicoDTO.getStatus()));
		
		Tecnico tecnico = tecnicoService.findById(ordemServicoDTO.getTecnico());
		Cliente cliente = clienteService.findById(ordemServicoDTO.getCliente());
		
		ordemServico.setTecnico(tecnico);
		ordemServico.setCliente(cliente);
		
		return ordemServicoRepository.save(ordemServico);
	}

	
}
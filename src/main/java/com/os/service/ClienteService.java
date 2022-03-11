package com.os.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.os.domain.Cliente;
import com.os.dtos.ClienteDTO;
import com.os.repository.ClienteRepository;
import com.os.resources.exceptions.DataIntegratyViolationException;
import com.os.service.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente findById(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		
		return cliente.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}
	
	public Cliente create(ClienteDTO clienteDto) {
		if(findByCPF(clienteDto) != null){
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		return clienteRepository.save(new Cliente(null, clienteDto.getNome(), clienteDto.getCpf(), clienteDto.getTelefone()));
	}
	
	public Cliente update(Integer id, @Valid ClienteDTO clienteDto) {
		Cliente clienteAtualizado = findById(id);
		
		if(findByCPF(clienteDto) != null && findByCPF(clienteDto).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		clienteAtualizado.setNome(clienteDto.getNome());
		clienteAtualizado.setCpf(clienteDto.getCpf());
		clienteAtualizado.setTelefone(clienteDto.getTelefone());
		
		return clienteRepository.save(clienteAtualizado);
	}

	
	private Cliente findByCPF(ClienteDTO clienteDto) {
		Cliente cliente = clienteRepository.findByCpf(clienteDto.getCpf());
		if(cliente != null) {
			return cliente;
		}
		return null;
	}

	public void delete(Integer id) {
		clienteRepository.deleteById(id);
	}

	
}

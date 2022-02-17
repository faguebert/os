package com.os.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.os.domain.Cliente;
import com.os.domain.OrdemServico;
import com.os.domain.Tecnico;
import com.os.domain.enums.Prioridade;
import com.os.domain.enums.Status;
import com.os.repository.ClienteRepository;
import com.os.repository.OrdemServicoRepository;
import com.os.repository.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	public void instanciaDB() {
		Tecnico t1 = new Tecnico(null, "Fabrizio", "587.118.280-13", "(88)9888-8888");
		Cliente c1 = new Cliente(null, "Mateo", "884.621.010-77", "(99)9777-7777");
		OrdemServico os1 = new OrdemServico(null, Prioridade.ALTA, "Teste create OS", Status.ANDAMENTO, t1, c1);

		t1.getList().add(os1);
		c1.getList().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));
		ordemServicoRepository.saveAll(Arrays.asList(os1));
	}

}

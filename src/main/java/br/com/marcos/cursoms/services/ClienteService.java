package br.com.marcos.cursoms.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcos.cursoms.domain.Cliente;
import br.com.marcos.cursoms.dto.ClienteDTO;
import br.com.marcos.cursoms.repositories.ClienteRepository;

@Service
public class ClienteService {
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente buscarPoId(Integer id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
			
		return cliente.orElseThrow(() -> new ObjectNotFoundException(id, Cliente.class.getName()));
	}

	public List<Cliente> buscaTodos() {
		return clienteRepository.findAll();
	}

	public void autalizar(Integer id, ClienteDTO clienteDTO) {
		Cliente cliente = clienteRepository.getOne(id);
		cliente.setNome(clienteDTO.getNome());
		cliente.setEmail(clienteDTO.getEmail());
		clienteRepository.save(cliente);
	}


}

package br.com.marcos.cursoms.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marcos.cursoms.domain.Pedido;
import br.com.marcos.cursoms.repositories.PedidoRepository;

@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscarPoId(Integer id) {
		Optional<Pedido> pedido = pedidoRepository.findById(id);
			
		return pedido.orElseThrow(() -> new ObjectNotFoundException(id, Pedido.class.getName()));
	}

}

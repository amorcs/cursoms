package br.com.marcos.cursoms.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.marcos.cursoms.domain.Pedido;
import br.com.marcos.cursoms.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {
	
	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping(value="/{id}")
	public ResponseEntity<Pedido> buscaPorId(@PathVariable("id") Integer id){
		Pedido pedido = pedidoService.buscarPoId(id);
		return ResponseEntity.ok().body(pedido);
	}
}

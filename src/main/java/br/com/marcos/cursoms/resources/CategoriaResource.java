package br.com.marcos.cursoms.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.marcos.cursoms.domain.Categoria;
import br.com.marcos.cursoms.dto.CategoriaDTO;
import br.com.marcos.cursoms.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Categoria> find(@PathVariable("id") Integer  id) {
		
		Categoria categoria = categoriaService.buscarPorId(id);
		return ResponseEntity.ok().body(categoria);
	}
	
	@PostMapping
	public ResponseEntity<Void> inserirCategoria(@Valid @RequestBody CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);
		categoria = categoriaService.inserirCategoria(categoria);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(categoriaDTO.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> atualizarCategoria(@PathVariable Integer id,@Valid  @RequestBody CategoriaDTO categoriaDTO){
		Categoria categoria = categoriaService.fromDTO(categoriaDTO);
		categoria.setId(id);
		categoria = categoriaService.atualizarCategoria(categoria);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/{id}")
	@Transactional
	public ResponseEntity<Void> deletarCategoria(@PathVariable Integer id){
		categoriaService.deletarCategoria(id);
		return ResponseEntity.ok().build(); 
		
	}
	
	@GetMapping()
	public ResponseEntity<List<CategoriaDTO>> listarCategorias(){
		List<Categoria> categorias = categoriaService.buscarTodos();
		
		List<CategoriaDTO> categoriasDTO = categorias.stream().map(categoria -> new CategoriaDTO(categoria)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(categoriasDTO);
		
	}
	@GetMapping(value="/page")
	public ResponseEntity<Page<CategoriaDTO>> buscaPaginada(
				@RequestParam(value = "page", defaultValue = "0") Integer page, 
				@RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage, 
				@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
				@RequestParam(value = "direction", defaultValue = "ASC")String direction){
		
		Page<Categoria> categorias = categoriaService.findPage(page, linesPerPage, orderBy, direction);
		
		Page<CategoriaDTO> categoriasDTO = categorias.map(categoria -> new CategoriaDTO(categoria));
		
		return ResponseEntity.ok().body(categoriasDTO);
		
	}
}

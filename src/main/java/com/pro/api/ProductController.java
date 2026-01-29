package com.pro.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.dto.ProductDTO;
import com.pro.dto.assembler.ProductDTOAssembler;
import com.pro.model.Product;
import com.pro.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductDTOAssembler productDTOAssembler;

	// CREATE - Criação de um novo produto
	@PostMapping
	public EntityModel<ProductDTO> createProduct(@RequestBody Product product) {
		Product savedProduct = productRepository.save(product);

		// Adicionando os links HATEOAS
		return productDTOAssembler.toModel(savedProduct);
	}

	// READ - Listar todos os produtos
	@GetMapping
	public CollectionModel<EntityModel<ProductDTO>> getAllProducts() {
		List<EntityModel<ProductDTO>> products = productRepository.findAll().stream().map(productDTOAssembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(products);
	}

	// READ - Obter um produto específico
	@GetMapping("/{id}")
	public EntityModel<ProductDTO> getProduct(@PathVariable Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Produto não encontrado"));

		return productDTOAssembler.toModel(product);
	}

	// UPDATE - Atualizar um produto existente
	@PutMapping("/{id}")
	public EntityModel<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Produto não encontrado"));

		product.setName(updatedProduct.getName());
		product.setPrice(updatedProduct.getPrice());

		Product savedProduct = productRepository.save(product);

		return productDTOAssembler.toModel(savedProduct);
	}

	// DELETE - Deletar um produto
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        productRepository.delete(product);
		
        //return ResponseEntity.ok("Produto excluído com sucesso");
        return null;
    }
}
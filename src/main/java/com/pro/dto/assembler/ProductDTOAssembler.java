package com.pro.dto.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.pro.api.ProductController;
import com.pro.dto.ProductDTO;
import com.pro.model.Product;

@Component
public class ProductDTOAssembler implements RepresentationModelAssembler<Product, EntityModel<ProductDTO>> {

	@Override
	public EntityModel<ProductDTO> toModel(Product product) {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
		productDTO.setName(product.getName());
		productDTO.setPrice(product.getPrice());

		// Criando links HATEOAS
		EntityModel<ProductDTO> productResource = EntityModel.of(productDTO);

		// Link self (auto referenciado)
		productResource.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getProduct(product.getId())).withSelfRel());

		// Link para todos os produtos
		productResource.add(WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class).getAllProducts()).withRel("all-products"));

		// Link para atualizar o produto (update)
        productResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class)
                .updateProduct(product.getId(), product)).withRel("update-product"));

        // Link para deletar o produto (delete)
        productResource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ProductController.class)
                .deleteProduct(product.getId())).withRel("delete-product"));

		return productResource;
	}
}
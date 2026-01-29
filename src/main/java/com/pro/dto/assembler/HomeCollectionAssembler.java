package com.pro.dto.assembler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.pro.api.HomeApi;
import com.pro.dto.HomeDTO;

@Component
public class HomeCollectionAssembler {

    private final HomeDTOAssembler homeDTOAssembler;

    // Injeção do assembler individual
    public HomeCollectionAssembler(HomeDTOAssembler homeDTOAssembler) {
        this.homeDTOAssembler = homeDTOAssembler;
    }

    public CollectionModel<EntityModel<HomeDTO>> toCollectionModel(Iterable<? extends HomeDTO> entities, int page, int size) {
        List<EntityModel<HomeDTO>> entityModels = StreamSupport.stream(entities.spliterator(), false)
            .map(homeDTOAssembler::toModel)  // Usa o assembler do item para cada item
            .collect(Collectors.toList());

        // Link 'self' para a coleção de recursos
        return CollectionModel.of(entityModels,
                WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HomeApi.class).getAllMessages(page, size)).withSelfRel());
    }
}
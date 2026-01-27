package com.pro.dto.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.pro.api.HomeApi;
import com.pro.dto.HomeDTO;

@Component
public class HomeDTOAssembler implements RepresentationModelAssembler<HomeDTO, EntityModel<HomeDTO>> {

    @Override
    public EntityModel<HomeDTO> toModel(HomeDTO homeDTO) {
        return EntityModel.of(homeDTO,
        		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HomeApi.class).index()).withSelfRel(),// Link para o próprio recurso
        		WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HomeApi.class).getAllMessages()).withRel("all-messages"));// Link para todas as mensagens
    }
}
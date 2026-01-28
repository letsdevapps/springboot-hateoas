package com.pro.dto.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.pro.api.HomeApi;
import com.pro.dto.HomeDTO;

@Component
public class HomeDTOAssembler implements RepresentationModelAssembler<HomeDTO, EntityModel<HomeDTO>> {

    @Override
    public EntityModel<HomeDTO> toModel(HomeDTO homeDTO) {
        return EntityModel.of(homeDTO,
        		linkTo(methodOn(HomeApi.class).index()).withRel("index"),// Link para o próprio recurso
        		linkTo(methodOn(HomeApi.class).getAllMessagesAssembler()).withRel("all-messages")// Link para todas as mensagens
        		);
    }
}
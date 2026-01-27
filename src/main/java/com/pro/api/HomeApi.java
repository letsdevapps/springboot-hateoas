package com.pro.api;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.dto.HomeDTO;
import com.pro.dto.assembler.HomeDTOAssembler;
import com.pro.service.HomeService;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api")
public class HomeApi {

	@Autowired
	private HomeService homeService;

	@Autowired
	private HomeDTOAssembler homeDTOAssembler;

	@GetMapping({ "", "/", "/index" })
	public EntityModel<HomeDTO> index() {
		HomeDTO homeDto = new HomeDTO("Springboot HATEOAS Home | Index");

		return EntityModel.of(homeDto,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HomeApi.class).index()).withSelfRel());
	}

	@GetMapping("/messages")
	public CollectionModel<EntityModel<HomeDTO>> getAllMessages() {
		List<HomeDTO> messages = homeService.getAllMessages();

		List<EntityModel<HomeDTO>> homeResources = messages.stream()
				.map((Function<HomeDTO, EntityModel<HomeDTO>>) message -> EntityModel.of(message, WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(HomeApi.class).getAllMessages()).withRel("all-messages")))
				.collect(Collectors.toList());

		return CollectionModel.of(homeResources,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HomeApi.class).getAllMessages()).withSelfRel());
	}

	@GetMapping("/messages/assembler")
	public CollectionModel<EntityModel<HomeDTO>> getAllMessagesAssembler() {
		List<HomeDTO> messages = homeService.getAllMessages();

		List<EntityModel<HomeDTO>> homeResources = messages.stream().map(homeDTOAssembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(homeResources,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HomeApi.class).getAllMessages()).withSelfRel());
	}
}
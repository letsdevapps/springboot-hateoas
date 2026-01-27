package com.pro.api;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pro.dto.HomeDTO;

//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api")
public class HomeApi {

	@GetMapping({ "", "/", "/index" })
	public EntityModel<HomeDTO> index() {
		HomeDTO homeDto = new HomeDTO("Springboot HATEOAS Home | Index");

		return EntityModel.of(homeDto,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(HomeApi.class).index()).withSelfRel());
	}
}
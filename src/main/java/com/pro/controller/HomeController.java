package com.pro.controller;

import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping({ "", "/" })
public class HomeController {

	private final RestTemplate restTemplate;

    public HomeController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

	@RequestMapping(value = { "", "/", "/home", "/index" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String index(Model model,
			@RequestParam(defaultValue = "") String searchText,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
		log.info("----- Springboot HATEOAS Controller Home | Index -----");
		
		String url = "http://localhost:8080/api/messages/pag?page="+ page + "&size=" + size + "&searchText=" + searchText;
		
//		if (searchText != null && !searchText.isEmpty()) {
//            url += "&searchText=" + searchText;
//        }
		
		ParameterizedTypeReference<Map<String, Object>> typeRef = new ParameterizedTypeReference<Map<String, Object>>() {};
	    
	    ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.GET ,null ,typeRef);

	    Map<String, Object> body = response.getBody();

        model.addAttribute("apiResponse", body);
        model.addAttribute("searchText", searchText);
		model.addAttribute("index", true);
		return "web/home/home";
	}
}
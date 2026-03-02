package com.pro.controller;

import java.util.Map;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.pro.model.Product;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final RestTemplate restTemplate;

    public ProductController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @GetMapping({"", "/"})
    public String listProducts(Model model) {

        String apiUrl = "http://localhost:8080/api/products";

        ResponseEntity<Map> response = restTemplate.getForEntity(apiUrl, Map.class);

        model.addAttribute("apiResponse", response.getBody());

        return "web/product/list";
    }
    
    @GetMapping("/edit")
    public String editProduct(@RequestParam String href, Model model) {

        ResponseEntity<Map> response = restTemplate.getForEntity(href, Map.class);

        model.addAttribute("product", response.getBody());

        return "web/product/form";
    }
    
    @PostMapping("/edit")
    public String updateProduct(@RequestParam String href, @ModelAttribute Product product) {
        restTemplate.put(href, product);  // Atualiza o produto via PUT na API
        return "redirect:/products";  // Redireciona para a lista de produtos
    }
    
    @GetMapping("/delete")
    public String deleteProduct(@RequestParam String href) {

        restTemplate.delete(href);

        return "redirect:/products";
    }
}
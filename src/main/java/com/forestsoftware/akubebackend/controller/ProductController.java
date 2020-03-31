package com.forestsoftware.akubebackend.controller;


import com.forestsoftware.akubebackend.model.Product;
import com.forestsoftware.akubebackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/greeting")
    public ResponseEntity<Map<String,Object>> getGreeting(){
        Map<String, Object>response = new HashMap<>();
        response.put("error", false);
        response.put("message", "Greetings from Akubeng");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody Product product){
        return new ResponseEntity(productRepository.save(product), HttpStatus.OK);


    }
}

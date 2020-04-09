package com.forestsoftware.akubebackend.controller;

import com.forestsoftware.akubebackend.model.Product;
import com.forestsoftware.akubebackend.model.image;
import com.forestsoftware.akubebackend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/images")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;
    @GetMapping("/all")
    public ResponseEntity getAllImages(){
        return new ResponseEntity(imageRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<List<image>>getProductImages(@PathVariable Long id){
        return  new ResponseEntity<>(imageRepository.findByProduct_Id(id), HttpStatus.OK);
    }
}

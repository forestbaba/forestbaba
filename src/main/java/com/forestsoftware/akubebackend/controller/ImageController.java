package com.forestsoftware.akubebackend.controller;

import com.forestsoftware.akubebackend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;
    @GetMapping("/all")
    public ResponseEntity getAllImages(){
        return new ResponseEntity(imageRepository.findAll(), HttpStatus.OK);
    }
}

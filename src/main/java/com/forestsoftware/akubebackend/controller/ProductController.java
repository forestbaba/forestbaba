package com.forestsoftware.akubebackend.controller;


import com.forestsoftware.akubebackend.model.Product;
import com.forestsoftware.akubebackend.model.UploadFileResponse;
import com.forestsoftware.akubebackend.model.image;
import com.forestsoftware.akubebackend.repository.ImageRepository;
import com.forestsoftware.akubebackend.repository.ProductRepository;
import com.forestsoftware.akubebackend.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ImageRepository imageRepository;
    @Autowired
    FileStorageService fileStorageService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/greeting")
    public ResponseEntity<Map<String, Object>> getGreeting() {
        Map<String, Object> response = new HashMap<>();
        response.put("error", false);
        response.put("message", "Greetings from Akubeng");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity addProduct(@RequestBody Product product) {
        return new ResponseEntity(productRepository.save(product), HttpStatus.OK);
    }

    @GetMapping("/getSingleProduct/{id}")
    public ResponseEntity getSingleProduct(@PathVariable Long id) {
        return new ResponseEntity(productRepository.findById(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity getAllProducts() {
        return new ResponseEntity(productRepository.findAll(), HttpStatus.OK);
    }

    @PutMapping("/updateOne/{id}")
    public ResponseEntity updateOne(@RequestBody Product product, @PathVariable Long id){

        System.out.println("Item: "+ product);
//        return  null;
       return productRepository.findById(id).map(product1 ->{
            product1.setCategory(product.getCategory());
            product1.setDescription(product.getDescription());
            product1.setPrice(product.getPrice());
            product1.setSize(product.getSize());
            product1.setName(product.getName());
            return new ResponseEntity(productRepository.save(product1),HttpStatus.OK);
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Error updating product")));
    }

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String product) {
        String fileName = fileStorageService.storeFile(file);
        Long productId = Long.parseLong(product);
        image image = new image();
        return productRepository.findById(productId).map(savedproduct -> {
            System.out.println("==+===: " + savedproduct);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/product/downloadFile/")
                    .path(fileName)
                    .toUriString();

            image.setProduct(savedproduct);
            image.setImageUrl(fileDownloadUri);
            imageRepository.save(image);
            savedproduct.setImage(fileDownloadUri);
            productRepository.save(savedproduct);
            return new UploadFileResponse(fileName, fileDownloadUri,
                    file.getContentType(), file.getSize());
        }).orElseThrow(() -> new ResourceNotFoundException(String.format("Error uploading file")));

    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/getAllNewin")
    public ResponseEntity getAllNewin() {
        return new ResponseEntity(productRepository.findAll(), HttpStatus.OK);

    }
}



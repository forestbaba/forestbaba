package com.forestsoftware.akubebackend.controller;

import com.forestsoftware.akubebackend.model.Product;
import com.forestsoftware.akubebackend.model.image;
import com.forestsoftware.akubebackend.repository.ImageRepository;
import com.forestsoftware.akubebackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/images")
public class ImageController {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    ProductRepository productRepository;
    @GetMapping("/all")
    public ResponseEntity getAllImages(){
        return new ResponseEntity(imageRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping("/product/{id}")
    public ResponseEntity<List<image>>getProductImages(@PathVariable Long id){
        return  new ResponseEntity<>(imageRepository.findByProduct_Id(id), HttpStatus.OK);
    }

    @PostMapping("/deleteImage")
    public ResponseEntity deleteProductImage(@RequestParam String image, String productImage,
                                             Long imageId, Long productId, Product productForm, String productName ){


        System.out.println("+++Image string: "+productName);
        System.out.println("+++product Image: "+productImage);
        System.out.println("+++Image id: "+imageId);
        System.out.println("+++Product Id: "+productId);
        Optional url = imageRepository.findById(imageId);
        List<image> imageList = imageRepository.findByProduct_Id(productId);
        Product product = new Product();
        Optional prod = productRepository.findById(productId);


        if(image.toString().equals(productImage.toString())){
            if(imageList.size() == 0){
                product.setImage("");
                productRepository.save(product);
            }else {
                System.out.println("=========>"+imageList.get(0).getImageUrl());
                if(imageList.get(0).getImageUrl().toString().equals(productImage)){
                    product.setImage(imageList.get(1).getImageUrl().toString());
                    product.setName(productName);
                    productRepository.save(product);
                }else {
                    product.setImage(imageList.get(0).getImageUrl().toString());
                    product.setName(productName);
                    productRepository.save(product);
                }
            }
            System.out.println("Yes they are same, delete");
            System.out.println("*******"+imageList);
        }else {
            if(imageList.size() == 0){
                product.setImage("");
                product.setName(productName);
                productRepository.save(product);
            }else {
                if(imageList.get(0).getImageUrl().toString().equals(productImage)){
                    product.setImage(imageList.get(1).getImageUrl().toString());
                    product.setName(productName);
                    productRepository.save(product);
                }else {
                    product.setImage(imageList.get(0).getImageUrl().toString());
                    product.setName(productName);
                    productRepository.save(product);
                }
            }
        }
//        else {
//            List<image> imageList = imageRepository.findByProduct_Id(productId);
//            System.out.println("*******"+imageList);
//            System.out.println("=========No");
//        }


        imageRepository.deleteById(imageId);
        return new ResponseEntity("Image deleted successfully", HttpStatus.OK);

    }
}

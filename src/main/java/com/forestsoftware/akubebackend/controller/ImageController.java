package com.forestsoftware.akubebackend.controller;

import com.forestsoftware.akubebackend.model.Product;
import com.forestsoftware.akubebackend.model.image;
import com.forestsoftware.akubebackend.repository.ImageRepository;
import com.forestsoftware.akubebackend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/deleteImage/{imageId}")
    public ResponseEntity deleteProductImage(@PathVariable Long imageId, @RequestBody Product product ){


        Optional<image> url = imageRepository.findById(imageId);
//        System.out.println("The product id: "+product.getId());
        Optional<Product>actualProduct = productRepository.findById(product.getId());
        List<image> imageList = imageRepository.findAllByProduct(product);

        System.out.println("Image urL: "+url.get().getImageUrl());
        System.out.println("Image List: "+actualProduct.get().getImage());

        return productRepository.findById(product.getId()).map(actual ->{

            if(imageList.size() > 0) {


                if (url.get().getImageUrl().toString().equals(actualProduct.get().getImage().toString())) {
                    System.out.println("Equality");
                    if (imageList.get(0).getImageUrl().equals(url.get().getImageUrl())) {
                        actual.setName(product.getName());
                        actual.setImage(imageList.get(1).getImageUrl());
                        productRepository.save(actual);
                        System.out.println("==");
                    } else {
                        actual.setName(product.getName());
                        actual.setImage(imageList.get(0).getImageUrl().toString());
                        productRepository.save(actual);
                        System.out.println("====" + imageList.get(0).getImageUrl().toString());
                        System.out.println("=||=");

                    }

                } else {
                    actual.setName(product.getName());
                    actual.setImage(imageList.get(0).getImageUrl().toString());
                    productRepository.save(actual);
                    System.out.println("====" + imageList.get(0).getImageUrl().toString());
                    System.out.println("=||=");

                }
            }else {
                System.out.println("No");
                actual.setName(product.getName());
                actual.setImage("");
              productRepository.save(actual);
            }

            imageRepository.deleteById(imageId);
            return new ResponseEntity("Image deleted successfully", HttpStatus.OK);


        }).orElse( new ResponseEntity("Unable toperform operation", HttpStatus.BAD_REQUEST));

//        Product product1 = new Product();
//        Optional prod = productRepository.findById(productId);


//        if(image.toString().equals(productImage.toString())){
//            if(imageList.size() == 0){
//                product.setImage("");
//                productRepository.save(product);
//            }else {
//                System.out.println("=========>"+imageList.get(0).getImageUrl());
//                if(imageList.get(0).getImageUrl().toString().equals(productImage)){
//                    product.setImage(imageList.get(1).getImageUrl().toString());
//                    product.setName(productName);
//                    productRepository.save(product);
//                }else {
//                    product.setImage(imageList.get(0).getImageUrl().toString());
//                    product.setName(productName);
//                    productRepository.save(product);
//                }
//            }
//            System.out.println("Yes they are same, delete");
//            System.out.println("*******"+imageList);
//        }else {
//            if(imageList.size() == 0){
//                product.setImage("");
//                product.setName(productName);
//                productRepository.save(product);
//            }else {
//                if(imageList.get(0).getImageUrl().toString().equals(productImage)){
//                    product.setImage(imageList.get(1).getImageUrl().toString());
//                    product.setName(productName);
//                    productRepository.save(product);
//                }else {
//                    product.setImage(imageList.get(0).getImageUrl().toString());
//                    product.setName(productName);
//                    productRepository.save(product);
//                }
//            }
//        }



    }
}

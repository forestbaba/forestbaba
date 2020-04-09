package com.forestsoftware.akubebackend.repository;

import com.forestsoftware.akubebackend.model.Product;
import com.forestsoftware.akubebackend.model.image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository  extends JpaRepository<image, Long> {

    List<image>findByProduct_Id(Long id);
}

package com.forestsoftware.akubebackend.repository;

import com.forestsoftware.akubebackend.model.image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository  extends JpaRepository<image, Long> {

}

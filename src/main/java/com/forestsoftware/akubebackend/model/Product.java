package com.forestsoftware.akubebackend.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = "products")
@Data
public class Product extends AuditModel{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull
    @Size(max = 20)
    public String name;

    public String description;
    public String size;
    public String price;
    public String image;
}

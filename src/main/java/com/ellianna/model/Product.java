package com.ellianna.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;
    private Long price;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

    @ManyToOne
    private ProductCategory productCategory;
    
    private Boolean available;
    private Boolean isCustomized;
}

package com.ellianna.service;

import com.ellianna.DTO.ProductDTO;
import com.ellianna.model.Product;
import com.ellianna.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product addProduct(Product product){
        if (product.getAvailable() == null) product.setAvailable(false);
        if (product.getIsCustomized() == null) product.setIsCustomized(false);

        return productRepository.save(product);
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> getProduct(){
        return productRepository.findAll();
    }

    public void deleteProduct(){
        productRepository.deleteAll();
    }

    public void deleteProductById(Long id){
        if (!productRepository.existsById(id))
            throw new EntityNotFoundException("Product with id "+ id+ " does not exist!");
        productRepository.deleteById(id);
    }

    @Transactional
    public Product updateProduct(Long id, ProductDTO.UpdateProductDTO updateProductDTO){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id "+id+ " does not exist!"));
        if (updateProductDTO.name != null){
            product.setName(updateProductDTO.name);
        }
        if (updateProductDTO.description != null){
            product.setDescription(updateProductDTO.description);
        }
        if (updateProductDTO.price != null){
            product.setPrice(updateProductDTO.price);
        }
        if (updateProductDTO.available != null){
            product.setAvailable(updateProductDTO.available);
        }
        if (updateProductDTO.isCustomized != null){
            product.setIsCustomized(updateProductDTO.isCustomized);
        }

        return productRepository.save(product);
    }
}

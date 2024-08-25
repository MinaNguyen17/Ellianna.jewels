package com.ellianna.controller;

import com.ellianna.DTO.ProductDTO;
import com.ellianna.model.Product;
import com.ellianna.model.ProductImage;
import com.ellianna.repository.ProductImageRepository;
import com.ellianna.repository.ProductRepository;
import com.ellianna.service.ProductService;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public ModelMapper modelMapper;
    @Autowired
    private ProductImageRepository productImageRepository;

    @PostMapping
    public ResponseEntity<?> addProduct(
        @RequestBody @Valid ProductDTO.CreateProductDTO createProductDTO) throws java.io.IOException{
        Product entity = modelMapper.map(createProductDTO, Product.class);
        return new ResponseEntity<>(productService.addProduct(entity),HttpStatus.OK) ;
    }

    @GetMapping
    public ResponseEntity<?> getProduct(){
        return new ResponseEntity<>(productService.getProduct(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getProductById(@PathVariable(value = "id") Long id) {
        Optional<Product> productOptional = productService.getProductById(id);
        if (productOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(productOptional.get(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(){
        productService.deleteProduct();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable("id") Long id){
        try {
            productService.deleteProductById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductById(
            @PathVariable("id") Long id,
            @Valid @RequestBody ProductDTO.UpdateProductDTO productDTO) {
        return new ResponseEntity<>(productService.updateProduct(id, productDTO), HttpStatus.OK);
    }

    // Image handle //

    // Add product images
    @PostMapping("/{id}/images")
    public ResponseEntity<?> addImage(
        @PathVariable Long id,
        @RequestParam("images") List<MultipartFile> file
        ) throws IOException{
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        List<ProductImage> images = product.getProductImages();
        if (file != null) {
            for (MultipartFile image : file){
                ProductImage productImage = new ProductImage();
                productImage.setImageData(image.getBytes());
                productImage.setImageType(image.getContentType());
                productImageRepository.save(productImage);
                images.add(productImage);
            }
        }
        productRepository.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    // Get product image by index
    @GetMapping("/{id}/images/{imageIndex}")
    public ResponseEntity<byte[]> getProductImage(@PathVariable Long id, @PathVariable int imageIndex) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        List<ProductImage> images = product.getProductImages();
        if (imageIndex < 0 || imageIndex >= images.size()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ProductImage image = images.get(imageIndex);
        String imageType = image.getImageType().replace("image/", "");
        MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;

        switch (imageType.toLowerCase()) {
            case "png":
                mediaType = MediaType.IMAGE_PNG;
                break;
            case "jpeg":
            case "jpg":
                mediaType = MediaType.IMAGE_JPEG;
                break;
            case "gif":
                mediaType = MediaType.IMAGE_GIF;
                break;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);  // Assuming the images are in JPEG format
        return new ResponseEntity<>(image.getImageData(), headers, HttpStatus.OK);
    }

    // Delete image by Id

    @DeleteMapping("/{id}/images/{imageIndex}")
    public ResponseEntity<?> deleteImage(
        @PathVariable Long id, 
        @PathVariable int imageIndex
        ){
            Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
            List<ProductImage> images = product.getProductImages();
            if (imageIndex < 0 || imageIndex >= images.size()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            ProductImage image = images.get(imageIndex);
            productImageRepository.delete(image);
            images.remove(imageIndex);
            product.setProductImages(images);
            productRepository.save(product);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

}


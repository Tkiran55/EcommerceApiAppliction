package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    //create
    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    //read
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    //read by id
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    //update
    public Product updateProduct(Long id, Product productDetails){
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("Product not found this id: " + id));

        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        return productRepository.save(product);
    }

    // --- D (DELETE) ---
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found for this id: " + id));

        productRepository.delete(product);
    }
}

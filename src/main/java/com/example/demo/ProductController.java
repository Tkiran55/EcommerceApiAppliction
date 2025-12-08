package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. CREATE (POST) - URL: /api/products
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201
    public Product createProduct(@RequestBody Product product) {
        return productService.createProduct(product);
    }

    // 2. READ ALL (GET) - URL: /api/products
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // 3. READ BY ID (GET) - URL: /api/products/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) {
        return productService.getProductById(productId)
                .map(product -> ResponseEntity.ok().body(product))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 4. UPDATE (PUT) - URL: /api/products/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable(value = "id") Long productId,
            @RequestBody Product productDetails) {

        // The service layer handles the 'not found' exception and returns the updated product
        try {
            Product updatedProduct = productService.updateProduct(productId, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. DELETE (DELETE) - URL: /api/products/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable(value = "id") Long productId) {
        try {
            productService.deleteProduct(productId);
            return ResponseEntity.noContent().build(); // HTTP 204 No Content
        } catch (RuntimeException e) {
            // If the product wasn't found, you might still return a success code
            // (or 404 depending on preference), but here we handle it.
            return ResponseEntity.notFound().build();
        }
    }
}



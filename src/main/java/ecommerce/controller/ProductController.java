package ecommerce.controller;

import ecommerce.entity.Product;
import ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private  final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        // Delegate to the service, then use the Optional API to handle the result.
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(product))           // If product is found, return 200 OK.
                .orElseGet(() -> ResponseEntity.notFound().build()); // If not found, return 404 Not Found.
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product){
        return productService.createProduct(product);
    }
}

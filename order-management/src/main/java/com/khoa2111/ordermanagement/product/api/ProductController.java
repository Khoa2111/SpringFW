package com.khoa2111.ordermanagement.product.api;

import com.khoa2111.ordermanagement.product.Product;
import com.khoa2111.ordermanagement.product.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductRepository productRepository;

  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Product create(@Valid @RequestBody CreateProductRequest req) {
    Product p = new Product();
    p.setName(req.name());
    p.setPrice(req.price());
    p.setStockQuantity(req.stockQuantity());
    return productRepository.save(p);
  }

  @GetMapping
  public List<Product> list() {
    return productRepository.findAll();
  }
}

package com.khoa2111.ordermanagement;

import com.khoa2111.ordermanagement.product.Product;
import com.khoa2111.ordermanagement.product.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class SeedDataConfig {
  @Bean
  CommandLineRunner seedProducts(ProductRepository repo) {
    return args -> {
      if (repo.count() > 0) return;

      Product p1 = new Product();
      p1.setName("Keyboard");
      p1.setPrice(new BigDecimal("20.00"));
      p1.setStockQuantity(5);

      Product p2 = new Product();
      p2.setName("Mouse");
      p2.setPrice(new BigDecimal("10.00"));
      p2.setStockQuantity(3);

      repo.save(p1);
      repo.save(p2);
    };
  }
}

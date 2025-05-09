package com.example.qwer.product;

import com.example.qwer.product.Product;
import com.example.qwer.product.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Optional<Product> findById(int id) {
        return repository.findById(id);
    }

    public List<Product> findByCategory(String category) {
        return repository.findByCategory(category);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }
}
package com.example.qwer.product;

import com.example.qwer.product.Product;
import com.example.qwer.product.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // 상품 추가
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return service.save(product);
    }

    // 상품 전체 조회 또는 단건 조회
    @GetMapping
    public List<Product> getProducts(@RequestParam(required = false) Integer product_id) {
        if (product_id != null) {
            Optional<Product> product = service.findById(product_id);
            return product.map(List::of).orElse(List.of());
        }
        return service.findAll();
    }

    // 상품 삭제
    @DeleteMapping
    public void deleteProduct(@RequestParam int product_id) {
        service.deleteById(product_id);
    }

    // 카테고리 설정 (상품에 category 넣기)
    @PostMapping("/category")
    public Product assignCategory(@RequestParam int product_id, @RequestParam String category) {
        Product product = service.findById(product_id)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));
        product.setCategory(category);
        return service.save(product);
    }

    // 카테고리 제거 (상품에서 category 빼기)
    @DeleteMapping("/category")
    public Product removeCategory(@RequestParam int product_id) {
        Product product = service.findById(product_id)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));
        product.setCategory(null);
        return service.save(product);
    }

}
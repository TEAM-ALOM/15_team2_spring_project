package com.example.qwer.product;

import com.example.qwer.product.dto.ProductRequestDto;
import com.example.qwer.product.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품 생성
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto request) {
        return ResponseEntity.ok(new ProductResponseDto(productService.createProduct(request)));
    }

    // 전체 상품 조회
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.getAllProducts().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    // ID로 상품 조회
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        Product product = productService.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));
        return ResponseEntity.ok(new ProductResponseDto(product));
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    // 상품에 카테고리 지정
    @PostMapping("/category")
    public ResponseEntity<Product> assignCategory(@RequestParam Long product_id, @RequestParam String category) {
        Product product = productService.findById(product_id)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));

        product.setCategory(category);
        return ResponseEntity.ok(productService.save(product));
    }

    // 상품에서 카테고리 제거
    @DeleteMapping("/category")
    public ResponseEntity<Product> removeCategory(@RequestParam Long product_id) {
        Product product = productService.findById(product_id)
                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));

        product.setCategory(null);
        return ResponseEntity.ok(productService.save(product));
    }

}

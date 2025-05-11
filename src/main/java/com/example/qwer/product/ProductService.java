package com.example.qwer.product;

import com.example.qwer.product.dto.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    // 상품 생성
    public Product createProduct(ProductRequestDto dto) {
        // Category 객체 대신 String 카테고리명을 사용
        String categoryName = dto.getCategory();  // DTO도 수정되어야 함: getCategoryId() → getCategory()

        Product product = Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .category(categoryName)
                .build();

        return productRepository.save(product);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> findById(long id) {
        return productRepository.findById(id);
    }

    public void deleteById(long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

}

package com.example.qwer.cart;

import com.example.qwer.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CtoP")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CtoP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private Integer quantity;
}

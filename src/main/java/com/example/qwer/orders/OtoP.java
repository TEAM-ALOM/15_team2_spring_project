package com.example.qwer.orders;

import com.example.qwer.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "OtoP")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OtoP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    private Integer quantity;



}

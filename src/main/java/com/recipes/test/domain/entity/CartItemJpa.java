package com.recipes.test.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Getter
@Entity
@Table(
    name = CartItemJpa.TABLE_NAME,
    indexes = {
        @Index(name = "cart_items_cart_id_idx", columnList = "cart_id"),
        @Index(name = "cart_items_product_id_idx", columnList = "product_id")
    }
)
@NoArgsConstructor
public class CartItemJpa extends AbstractBaseJpa {

    protected static final String TABLE_NAME = "cart_items";

    @Column(name = "cart_id", insertable = false, updatable = false)
    private Long cartId;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties("carts")
    private CartJpa cart;

    @Column(name = "product_id", insertable = false, updatable = false)
    private Long productId;

    @NotNull
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties("products")
    private ProductJpa product;

    public CartItemJpa(@NonNull final CartJpa cart, @NonNull final ProductJpa product) {
        this.cart = cart;
        this.product = product;
    }
}

package com.recipes.test.gateway;

import com.recipes.test.domain.entity.CartJpa;
import com.recipes.test.repository.CartRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class CartGatewayImpl extends AbstractBaseGateway<CartJpa> implements CartGateway {

    public CartGatewayImpl(CartRepository repository) {
        super(repository);
    }
}

package com.recipes.test.gateway;

import com.recipes.test.domain.entity.ProductJpa;
import com.recipes.test.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ProductGatewayImpl extends AbstractBaseGateway<ProductJpa> implements ProductGateway {

    public ProductGatewayImpl(ProductRepository repository) {
        super(repository);
    }
}

package io.github.fernandoferreira.compasso.productms.converter;

import io.github.fernandoferreira.compasso.productms.controller.dto.ProductRequest;
import io.github.fernandoferreira.compasso.productms.model.Product;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter implements Converter<ProductRequest, Product> {

    @Override
    public Product convert(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
    }
}

package io.github.fernandoferreira.compasso.productms.controller;

import io.github.fernandoferreira.compasso.productms.controller.dto.ProductRequest;
import io.github.fernandoferreira.compasso.productms.controller.dto.ProductSearchResponse;
import io.github.fernandoferreira.compasso.productms.converter.ProductConverter;
import io.github.fernandoferreira.compasso.productms.model.Product;
import io.github.fernandoferreira.compasso.productms.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductConverter productConverter;

    @GetMapping
    public ResponseEntity<ProductSearchResponse> getProducts() {
        Set<Product> products = this.productService.findAll();

        return ResponseEntity.ok(ProductSearchResponse.builder().products(products).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Optional<Product> optional = this.productService.findById(id);

        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<ProductSearchResponse> search(@RequestParam(required = false, name = "q") String nameOrDescription,
                                               @RequestParam(required = false, name = "min_price") Double minPrice,
                                               @RequestParam(required = false, name = "max_price") Double maxPrice) {
        Set<Product> products = this.productService.searchBy(nameOrDescription, minPrice, maxPrice);
        return ResponseEntity.ok(ProductSearchResponse.builder().products(products).build());

    }

    @PostMapping
    @Transactional
    public ResponseEntity<Product> post(@Valid @RequestBody ProductRequest productRequest, UriComponentsBuilder uriBuilder) {
        Product product = this.productConverter.convert(productRequest);
        Product productSaved = this.productService.save(product);

        URI uri = uriBuilder.path("/product/{id}").buildAndExpand(productSaved.getId()).toUri();

        return ResponseEntity.created(uri).body(productSaved);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Product> update(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        Product product = this.productService.update(id, productRequest);

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Product deletedProduct = this.productService.deleteById(id);
        return ResponseEntity.ok().body(deletedProduct);
    }
}

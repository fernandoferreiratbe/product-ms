package io.github.fernandoferreira.compasso.productms.controllers;

import io.github.fernandoferreira.compasso.productms.controllers.dto.ProductRequest;
import io.github.fernandoferreira.compasso.productms.controllers.dto.ProductSearchResponse;
import io.github.fernandoferreira.compasso.productms.converters.ProductConverter;
import io.github.fernandoferreira.compasso.productms.models.Product;
import io.github.fernandoferreira.compasso.productms.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
    public ResponseEntity<ProductSearchResponse> findAll() {
        Set<Product> products = this.productService.findAll();

        return ResponseEntity.ok(ProductSearchResponse.builder().products(products).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        Optional<Product> product = this.productService.findById(id);

        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
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
    public ResponseEntity<Product> post(@Valid @RequestBody ProductRequest productRequest) {
        Product product = this.productConverter.convert(productRequest);
        Product productSaved = this.productService.save(product);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productSaved.getId())
                .toUri();

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

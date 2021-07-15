package io.github.fernandoferreira.compasso.productms.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Product {

    private Long id;
    private String name;
    private String description;
    private Double price;

}

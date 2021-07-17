package io.github.fernandoferreira.compasso.productms.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDto {
    private Integer status_code;
    private String message;
}

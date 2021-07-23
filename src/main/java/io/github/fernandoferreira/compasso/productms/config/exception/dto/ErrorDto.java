package io.github.fernandoferreira.compasso.productms.config.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ErrorDto {
    private Integer status_code;
    private String message;
}

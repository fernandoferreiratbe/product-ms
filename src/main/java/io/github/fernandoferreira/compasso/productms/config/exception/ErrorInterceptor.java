package io.github.fernandoferreira.compasso.productms.config.exception;

import io.github.fernandoferreira.compasso.productms.config.exception.dto.ErrorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorInterceptor {

    private final MessageSource messageSource;

    @Autowired
    public ErrorInterceptor(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDto> handle(MethodArgumentNotValidException exception) {
        List<ErrorDto> errors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach(e -> {
            String message = this.messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErrorDto error = new ErrorDto(HttpStatus.BAD_REQUEST.value(), message);

            errors.add(error);
        });

        return errors;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(ProductNotFoundException.class)
    public void handleProductNotFound(ProductNotFoundException exception) { }

}

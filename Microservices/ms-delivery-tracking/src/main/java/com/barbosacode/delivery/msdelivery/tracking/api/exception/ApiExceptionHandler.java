package com.barbosacode.delivery.msdelivery.tracking.api.exception;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {

        List<ApiError.FieldError> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ApiError.FieldError(error.getField(), error.getDefaultMessage())
                ).toList();


        ApiError apiError = new ApiError(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de validação",
                "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.",
                request.getRequestURI(),
                errors
        );
        return ResponseEntity
                .badRequest()
                .body(apiError);
    }
}

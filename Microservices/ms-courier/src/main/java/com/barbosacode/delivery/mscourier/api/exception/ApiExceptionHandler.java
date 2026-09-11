package com.barbosacode.delivery.mscourier.api.exception;


import com.barbosacode.delivery.mscourier.domain.exceptions.CourierNotFoundException;
import com.barbosacode.delivery.mscourier.domain.exceptions.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
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

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> handleDomainException(DomainException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro de negócio",
                exception.getMessage(),
                request.getRequestURI(),
                List.of()
        );
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiError> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                OffsetDateTime.now(),
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Método não permitido",
                "O método HTTP informado não é suportado para este recurso.",
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(apiError);
    }

    @ExceptionHandler(CourierNotFoundException.class)
    public ResponseEntity<ApiError> handleCourierNotFoundException(CourierNotFoundException exception, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                OffsetDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                "Entregador não encontrado",
                exception.getMessage(),
                request.getRequestURI(),
                List.of());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Parâmetro inválido",
                "Um ou mais parâmetros possuem um formato inválido.",
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                OffsetDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Requisição inválida",
                "O corpo da requisição possui um formato inválido.",
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnexpectedException(Exception exception, HttpServletRequest request) {

        ApiError apiError = new ApiError(
                OffsetDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                "Ocorreu um erro inesperado. Tente novamente mais tarde.",
                request.getRequestURI(),
                List.of()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
}

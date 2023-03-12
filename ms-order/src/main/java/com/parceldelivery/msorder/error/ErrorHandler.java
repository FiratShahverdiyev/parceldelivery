package com.parceldelivery.msorder.error;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<BaseErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(400).body(new BaseErrorResponse("Data not found",
                "MS_ORDER_001", ex.getMessage(), 400));
    }

    @ExceptionHandler(ProcessIsNotPossibleException.class)
    public ResponseEntity<BaseErrorResponse> handleProcessIsNotPossibleException(ProcessIsNotPossibleException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(400).body(new BaseErrorResponse("Process is not possible",
                "MS_ORDER_002", ex.getMessage(), 400));
    }

}
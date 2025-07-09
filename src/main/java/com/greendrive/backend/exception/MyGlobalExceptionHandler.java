package com.greendrive.backend.exception;

import com.greendrive.backend.payload.APIResponse;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MyGlobalExceptionHandler {

    // Handle validation errors (e.g. @NotBlank, @Size)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Handle custom API exceptions with custom status
    @ExceptionHandler(APIException.class)
    public ResponseEntity<APIResponse> handleAPIException(APIException e) {
        APIResponse apiResponse = new APIResponse(e.getMessage(), false);
        return ResponseEntity.status(e.getStatus()).body(apiResponse);
    }

    // Handle errors like sorting/filtering by invalid fields (Spring Data JPA)
    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<APIResponse> handlePropertyReferenceException(PropertyReferenceException e) {
        APIResponse apiResponse = new APIResponse(e.getMessage(), false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    // Generic fallback handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse> handleAllUncaughtExceptions(Exception e) {
        APIResponse apiResponse = new APIResponse(e.getMessage(), false);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiResponse);
    }
}
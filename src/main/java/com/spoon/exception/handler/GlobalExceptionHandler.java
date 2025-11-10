 
package com.spoon.exception.handler;

/**
 *
 * @author User
 */
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {

    public record ErrorResponse(int status, String error, String message) {}

    @ExceptionHandler(RecipeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecipeNotFound(RecipeNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "NOT_FOUND",
            ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ExternalApiException.class)
    public ResponseEntity<ErrorResponse> handleExternalApiError(ExternalApiException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.SERVICE_UNAVAILABLE.value(),
            "EXTERNAL_SERVICE_ERROR",
            "The external recipe service encountered an error."
        );
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }
    
    // Handle DTO validation failures (e.g., RecalculationRequest fields)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            "VALIDATION_ERROR",
            "Invalid request data: " + message
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
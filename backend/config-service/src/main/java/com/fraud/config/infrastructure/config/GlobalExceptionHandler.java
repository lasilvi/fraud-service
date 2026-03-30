package com.fraud.config.infrastructure.config;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, Object>> handleValidationError(MethodArgumentNotValidException exception) {
		Map<String, String> fields = new LinkedHashMap<>();
		exception.getBindingResult().getFieldErrors()
			.forEach(fieldError -> fields.put(fieldError.getField(), fieldError.getDefaultMessage()));

		Map<String, Object> body = Map.of(
			"error", "Validation failed",
			"fields", fields
		);

		return ResponseEntity.badRequest().body(body);
	}

	@ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
	public ResponseEntity<Map<String, String>> handleBadRequest(RuntimeException exception) {
		return ResponseEntity.badRequest().body(Map.of("error", exception.getMessage()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, String>> handleInternalError(Exception exception) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(Map.of("error", "Internal server error"));
	}
}

package org.pah_monitoring.main.controllers.interceptors;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.exceptions.rest.RestDataValidationException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(1000)
@PreAuthorize("permitAll()")
public class RestExceptionInterceptor {

    @ExceptionHandler(RestDataValidationException.class)
    public ResponseEntity<ExceptionEntity> restDataValidationException(RestDataValidationException e) {
        ExceptionEntity json = new ExceptionEntity(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @AllArgsConstructor
    public static class ExceptionEntity {

        private Integer errorStatusCode;

        private String errorDescription;

    }

}

package org.pah_monitoring.main.controllers.interceptors;

import lombok.*;
import org.pah_monitoring.auxiliary.exceptions.rest.DataDeletionRestException;
import org.pah_monitoring.auxiliary.exceptions.rest.DataHasNotBeenDeletedRestException;
import org.pah_monitoring.auxiliary.exceptions.rest.DataSavingRestException;
import org.pah_monitoring.auxiliary.exceptions.rest.common.RestException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(1000)
@PreAuthorize("permitAll()")
public class RestExceptionInterceptor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataSavingRestException.class, DataDeletionRestException.class})
    public ResponseEntity<ExceptionEntity> restException(RestException e) {
        ExceptionEntity json = new ExceptionEntity(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DataHasNotBeenDeletedRestException.class)
    public ResponseEntity<ExceptionEntity> dataHasNotBeenDeletedRestException(DataHasNotBeenDeletedRestException e) {
        ExceptionEntity json = new ExceptionEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class ExceptionEntity {

        private Integer errorStatusCode;

        private String errorDescription;

    }

}

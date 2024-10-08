package org.pah_monitoring.main.controllers.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.common.BadRequestRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.forbidden.NotEnoughRightsRestControllerException;
import org.pah_monitoring.main.exceptions.controller.rest.internal_server.common.InternalServerErrorRestControllerException;
import org.pah_monitoring.main.exceptions.email.EmailSendingException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@ControllerAdvice
@Order(1000)
@PreAuthorize("permitAll()")
@Slf4j
public class RestExceptionInterceptor {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestRestControllerException.class)
    public ResponseEntity<ExceptionEntity> badRequest(BadRequestRestControllerException e) {
        ExceptionEntity json = new ExceptionEntity(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException.class)
    public ResponseEntity<ExceptionEntity> badRequest(HttpMessageConversionException e) {
        ExceptionEntity json = new ExceptionEntity(HttpStatus.BAD_REQUEST.value(), "Ошибка преобразования между JSON и Java-объектом");
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionEntity> badRequest(MaxUploadSizeExceededException e) {
        ExceptionEntity json = new ExceptionEntity(HttpStatus.BAD_REQUEST.value(), "Превышен максимальный размер файла");
        return new ResponseEntity<>(json, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(NotEnoughRightsRestControllerException.class)
    public ResponseEntity<ExceptionEntity> forbidden(NotEnoughRightsRestControllerException e) {
        ExceptionEntity json = new ExceptionEntity(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return new ResponseEntity<>(json, HttpStatus.FORBIDDEN);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorRestControllerException.class)
    public ResponseEntity<ExceptionEntity> internalServerError(InternalServerErrorRestControllerException e) {
        log.error(e.getMessage(), e);
        ExceptionEntity json = new ExceptionEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(EmailSendingException.class)
    public ResponseEntity<ExceptionEntity> internalServerError(EmailSendingException e) {
        log.error(e.getMessage(), e);
        ExceptionEntity json = new ExceptionEntity(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return new ResponseEntity<>(json, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @NoArgsConstructor @AllArgsConstructor @Data
    public static class ExceptionEntity {
        private Integer errorStatusCode;
        private String errorDescription;
    }

}

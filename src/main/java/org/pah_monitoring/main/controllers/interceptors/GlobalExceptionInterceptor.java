package org.pah_monitoring.main.controllers.interceptors;

import jakarta.servlet.RequestDispatcher;
import org.pah_monitoring.main.exceptions.common.CheckedException;
import org.pah_monitoring.main.exceptions.common.UncheckedException;
import org.pah_monitoring.main.exceptions.controller.mvc.NotEnoughRightsMvcControllerException;
import org.pah_monitoring.main.exceptions.controller.mvc.UrlValidationMvcControllerException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Order(2000)
@PreAuthorize("permitAll()")
public class GlobalExceptionInterceptor {

    // todo: add exceptions to methods parameters for future use in logging (and add logging)

    @ExceptionHandler({
            ServerWebInputException.class,
            HttpClientErrorException.BadRequest.class,
            UrlValidationMvcControllerException.class
    })
    public String error400(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.BAD_REQUEST.value());
        return "redirect:/error";
    }

    @ExceptionHandler({
            HttpClientErrorException.Unauthorized.class
    })
    public String error401(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.UNAUTHORIZED.value());
        return "redirect:/error";
    }

    @ExceptionHandler({
            AccessDeniedException.class,
            HttpClientErrorException.Forbidden.class,
            NotEnoughRightsMvcControllerException.class
    })
    public String error403(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.FORBIDDEN.value());
        return "redirect:/error";
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpClientErrorException.NotFound.class
    })
    public String error404(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.NOT_FOUND.value());
        return "redirect:/error";
    }

    @ExceptionHandler({
            HttpRequestMethodNotSupportedException.class,
            HttpClientErrorException.MethodNotAllowed.class
    })
    public String error405(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.METHOD_NOT_ALLOWED.value());
        return "redirect:/error";
    }

    @ExceptionHandler({
            UncheckedException.class,
            CheckedException.class
    })
    public String unexpectedServerErrorByMyExceptions(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "redirect:/error";
    }

    @ExceptionHandler({
            Exception.class
    })
    public String unexpectedServerErrorByOtherExceptions(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute(RequestDispatcher.ERROR_STATUS_CODE, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "redirect:/error";
    }

}

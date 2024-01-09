package org.pah_monitoring.main.controllers.interceptors;

import org.pah_monitoring.auxiliary.exceptions.common.CheckedException;
import org.pah_monitoring.auxiliary.exceptions.common.UncheckedException;
import org.pah_monitoring.auxiliary.exceptions.controller.IncorrectUrlControllerException;
import org.pah_monitoring.auxiliary.exceptions.service.EntityNotFoundServiceException;
import org.pah_monitoring.auxiliary.text.HttpErrorText;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Order(2000)
@PreAuthorize("permitAll()")
public class GlobalExceptionInterceptor {

    // todo: add exceptions to methods parameters for future use in logging (and add logging)

    @ExceptionHandler({
            ServerWebInputException.class,
            HttpClientErrorException.BadRequest.class,
            IncorrectUrlControllerException.class
    })
    public String error400(Model model) {
        addToModel(model, HttpErrorText.title400, HttpErrorText.text400);
        return "errors/error";
    }

    @ExceptionHandler({HttpClientErrorException.Unauthorized.class})
    public String error401(Model model) {
        addToModel(model, HttpErrorText.title401, HttpErrorText.text401);
        return "errors/error";
    }

    @ExceptionHandler({AccessDeniedException.class, HttpClientErrorException.Forbidden.class})
    public String error403(Model model) {
        addToModel(model, HttpErrorText.title403, HttpErrorText.text403);
        return "errors/error";
    }

    @ExceptionHandler({
            NoHandlerFoundException.class,
            HttpClientErrorException.NotFound.class,
            EntityNotFoundServiceException.class
    })
    public String error404(Model model) {
        addToModel(model, HttpErrorText.title404, HttpErrorText.text404);
        return "errors/error";
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpClientErrorException.MethodNotAllowed.class})
    public String error405(Model model) {
        addToModel(model, HttpErrorText.title405, HttpErrorText.text405);
        return "errors/error";
    }

    @ExceptionHandler({UncheckedException.class, CheckedException.class})
    public String unexpectedServerErrorByMyExceptions(Model model) {
        addToModel(model, HttpErrorText.titleUnexpectedServerError, HttpErrorText.textUnexpectedServerError);
        return "errors/error";
    }

    @ExceptionHandler(Exception.class)
    public String unexpectedServerErrorByOtherExceptions(Model model) {
        addToModel(model, HttpErrorText.titleUnexpectedServerError, HttpErrorText.textUnexpectedServerError);
        return "errors/error";
    }

    private void addToModel(Model model, Object errorTitle, Object errorText) {
        model.addAttribute("errorTitle", errorTitle);
        model.addAttribute("errorText", errorText);
    }

}

package org.pah_monitoring.main.controllers.interceptors;

import org.pah_monitoring.auxiliary.text.HttpErrorText;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@PreAuthorize("permitAll()")
public class ExceptionInterceptor {

    @ExceptionHandler({AccessDeniedException.class, HttpClientErrorException.Unauthorized.class})
    public String error401(Model model) {
        addToModel(model, HttpErrorText.title401, HttpErrorText.text401);
        return "errors/error";
    }

    @ExceptionHandler(HttpClientErrorException.Forbidden.class)
    public String error403(Model model) {
        addToModel(model, HttpErrorText.title403, HttpErrorText.text403);
        return "errors/error";
    }

    @ExceptionHandler({NoHandlerFoundException.class, HttpClientErrorException.NotFound.class})
    public String error404(Model model) {
        addToModel(model, HttpErrorText.title404, HttpErrorText.text404);
        return "errors/error";
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpClientErrorException.MethodNotAllowed.class})
    public String error405(Model model) {
        addToModel(model, HttpErrorText.title405, HttpErrorText.text405);
        return "errors/error";
    }

    // todo: expected server error interception

    @ExceptionHandler(Exception.class)
    public String unexpectedServerError(Model model) {
        addToModel(model, HttpErrorText.titleUnexpectedServerError, HttpErrorText.textUnexpectedServerError);
        return "errors/error";
    }

    private void addToModel(Model model, Object errorTitle, Object errorText) {
        model.addAttribute("errorTitle", errorTitle);
        model.addAttribute("errorText", errorText);
    }

}

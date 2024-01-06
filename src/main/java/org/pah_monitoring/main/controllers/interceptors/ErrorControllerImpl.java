package org.pah_monitoring.main.controllers.interceptors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.pah_monitoring.auxiliary.exceptions.NotHandledErrorStatusCodeException;
import org.pah_monitoring.auxiliary.text.HttpErrorText;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
@PreAuthorize("permitAll()")
public class ErrorControllerImpl implements ErrorController {

    @RequestMapping
    public String errors(HttpServletRequest request, Model model) throws NotHandledErrorStatusCodeException {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        status = status == null ? "404" : status;
        switch (status.toString()) {
            case "401" -> addToModel(model, HttpErrorText.title401, HttpErrorText.text401);
            case "403" -> addToModel(model, HttpErrorText.title403, HttpErrorText.title403);
            case "404" -> addToModel(model, HttpErrorText.title404, HttpErrorText.title404);
            case "405" -> addToModel(model, HttpErrorText.title405, HttpErrorText.title405);
            case "500" -> addToModel(model, HttpErrorText.titleUnexpectedServerError, HttpErrorText.textUnexpectedServerError);
            default -> throw new NotHandledErrorStatusCodeException(
                    "There were no explicit mappings for error with status code %s".formatted(status)
            );
        }
        return "errors/error";
    }

    private void addToModel(Model model, Object errorTitle, Object errorText) {
        model.addAttribute("errorTitle", errorTitle);
        model.addAttribute("errorText", errorText);
    }

}

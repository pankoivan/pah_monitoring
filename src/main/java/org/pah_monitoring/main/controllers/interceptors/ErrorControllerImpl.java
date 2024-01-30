package org.pah_monitoring.main.controllers.interceptors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
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
    public String errors(HttpServletRequest request, Model model) {

        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String status = statusObj == null ? "404" : statusObj.toString();

        switch (status) {
            case "400" -> addToModel(model, HttpErrorText.title400, HttpErrorText.text400);
            case "401" -> addToModel(model, HttpErrorText.title401, HttpErrorText.text401);
            case "403" -> addToModel(model, HttpErrorText.title403, HttpErrorText.text403);
            case "404" -> addToModel(model, HttpErrorText.title404, HttpErrorText.text404);
            case "405" -> addToModel(model, HttpErrorText.title405, HttpErrorText.text405);
            case "500" -> addToModel(model, HttpErrorText.titleUnexpectedServerError, HttpErrorText.textUnexpectedServerError);
            default -> addToModel(model, HttpErrorText.titleXxx.formatted(status), HttpErrorText.textNotHandledXxx.formatted(status));
        }

        return "errors/error";

    }

    private void addToModel(Model model, Object errorTitle, Object errorText) {
        model.addAttribute("errorTitle", errorTitle);
        model.addAttribute("errorText", errorText);
    }

}

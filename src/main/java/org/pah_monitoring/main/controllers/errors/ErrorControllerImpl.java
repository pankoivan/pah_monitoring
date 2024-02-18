package org.pah_monitoring.main.controllers.errors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.pah_monitoring.auxiliary.text.HttpErrorText;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
@PreAuthorize("permitAll()")
public class ErrorControllerImpl implements ErrorController {

    @RequestMapping
    public String errors(Model model,
                         HttpServletRequest request,
                         HttpServletResponse response,
                         @ModelAttribute(RequestDispatcher.ERROR_STATUS_CODE) String statusCode) {

        String code = code(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE), statusCode);

        response.setStatus(Integer.parseInt(code));

        switch (code) {
            case "400" -> addToModel(model, HttpErrorText.title400, HttpErrorText.text400);
            case "401" -> addToModel(model, HttpErrorText.title401, HttpErrorText.text401);
            case "403" -> addToModel(model, HttpErrorText.title403, HttpErrorText.text403);
            case "404" -> addToModel(model, HttpErrorText.title404, HttpErrorText.text404);
            case "405" -> addToModel(model, HttpErrorText.title405, HttpErrorText.text405);
            case "500" -> addToModel(model, HttpErrorText.titleUnexpectedServerError, HttpErrorText.textUnexpectedServerError);
            default -> {
                if (code.matches("^4[0-9]{2}$")) {
                    addToModel(model, HttpErrorText.titleXxx.formatted(code), HttpErrorText.textNotHandledXxx.formatted(code));
                } else {
                    addToModel(model, HttpErrorText.titleUnexpectedServerError, HttpErrorText.textUnexpectedServerError);
                }
            }
        }

        return "errors/error";

    }

    private void addToModel(Model model, String errorTitle, String errorText) {
        model.addAttribute("errorTitle", errorTitle);
        model.addAttribute("errorText", errorText);
    }

    private String code(Object requestCode, String redirectCode) {
        return requestCode != null
                ? requestCode.toString()
                : !redirectCode.isEmpty()
                    ? redirectCode
                    : "404";
    }

}

package org.pah_monitoring.main.controllers.mvc;

import jakarta.servlet.http.HttpSession;
import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.MainAdministrator;
import org.pah_monitoring.main.entities.MainPageContact;
import org.pah_monitoring.main.repositorites.MainPageContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/main-page-contacts")
public class MainPageContactsController {

    @Autowired
    private MainPageContactRepository mainPageContactRepository;

    @RequestMapping
    public String returnMainPageContactsPage(Model model,
                                             HttpSession session,
                                             Authentication authentication,
                                             @SessionAttribute(value = "contact", required = false) MainPageContact contact)
            throws AuthenticationUtilsException {

        MainAdministrator mainAdministrator = AuthenticationUtils.extractCurrentUser(authentication, MainAdministrator.class);
        model.addAttribute("currentUser", mainAdministrator);
        model.addAttribute("nameLastname", "%s %s".formatted(
                mainAdministrator.getUserInformation().getName(),
                mainAdministrator.getUserInformation().getLastname()
        ));
        model.addAttribute("header1", "fragments/main-admin-header");
        model.addAttribute("header", "main-admin-header");

        model.addAttribute("contacts", mainPageContactRepository.findAll());

        model.addAttribute("contact", new MainPageContact());

        if (contact != null) {
            model.addAttribute("contact", contact);
            session.removeAttribute("contact");
        }

        return "main-page-contacts";
    }

    @PostMapping("/edit/{id}")
    public String redirectMainPageContactsPageForEditing(Model model,
                                                         HttpSession session,
                                                         @PathVariable("id") int id) {

        System.out.println("----" + mainPageContactRepository.findById(id));
        session.setAttribute("contact", mainPageContactRepository.findById(id).get());
        System.out.println(session.getAttribute("contact"));

        return "redirect:/main-page-contacts";
    }

    @PostMapping("/delete/{id}")
    public String redirectMainPageContactsPageAfterDeleting(@PathVariable("id") int id) {
        mainPageContactRepository.deleteById(id);
        return "redirect:/main-page-contacts";
    }

    @PostMapping("/save")
    public String redirectMainPageContactsPageAfterSaving(MainPageContact contact) {
        System.out.println("FOR SAVING: " + contact);
        mainPageContactRepository.save(contact);
        return "redirect:/main-page-contacts";
    }

}

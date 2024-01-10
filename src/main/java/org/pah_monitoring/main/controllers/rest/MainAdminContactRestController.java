package org.pah_monitoring.main.controllers.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.exceptions.rest.RestDataDeletionException;
import org.pah_monitoring.auxiliary.exceptions.rest.RestDataSavingException;
import org.pah_monitoring.main.entities.other.MainAdminContact;
import org.pah_monitoring.main.services.other.interfaces.MainAdminContactService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/contacts")
@PreAuthorize("permitAll()") // todo: only main admin
public class MainAdminContactRestController {

    private final MainAdminContactService service;

    @GetMapping
    public List<MainAdminContact> get() {
        return service.findAll();
    }

    @PostMapping
    public MainAdminContact add(@RequestBody @Valid MainAdminContact contact, BindingResult bindingResult) {
        if (service.isBindingResultHasErrors(bindingResult)) {
            throw new RestDataSavingException(service.bindingResultAnyErrorMessage(bindingResult));
        }
        if (!service.isValidForSaving(contact, bindingResult)) {
            throw new RestDataSavingException(service.bindingResultAnyErrorMessage(bindingResult));
        }
        return service.save(contact);
    }

    @PutMapping
    public MainAdminContact edit(@RequestBody @Valid MainAdminContact contact, BindingResult bindingResult) {
        if (service.isBindingResultHasErrors(bindingResult)) {
            throw new RestDataSavingException(service.bindingResultAnyErrorMessage(bindingResult));
        }
        if (!service.isValidForSaving(contact, bindingResult)) {
            throw new RestDataSavingException(service.bindingResultAnyErrorMessage(bindingResult));
        }
        return service.save(contact);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        // todo: check if id is correct, and convert it from String to real id (Integer)
        /*if (!service.deleteById(id)) {
            throw new RestDataDeletionException("Элемент не был удалён. Повторите попытку позже.");
        }*/
    }

}

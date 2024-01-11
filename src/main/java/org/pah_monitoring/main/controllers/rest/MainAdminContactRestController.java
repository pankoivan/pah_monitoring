package org.pah_monitoring.main.controllers.rest;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.pah_monitoring.main.exceptions.rest.bad_request.MalformedUrlRestException;
import org.pah_monitoring.main.exceptions.rest.internal_server.DataDeletionRestException;
import org.pah_monitoring.main.exceptions.rest.bad_request.DataValidationRestException;
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
        if (service.isNotValidForSaving(contact, bindingResult)) {
            throw new DataValidationRestException(service.bindingResultAnyErrorMessage(bindingResult));
        }
        return service.save(contact);
    }

    @PutMapping
    public MainAdminContact edit(@RequestBody @Valid MainAdminContact contact, BindingResult bindingResult) {
        if (service.isNotValidForSaving(contact, bindingResult)) {
            throw new DataValidationRestException(service.bindingResultAnyErrorMessage(bindingResult));
        }
        return service.save(contact);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String pathId) {
        int id = getId(pathId);
        if (!service.deleteById(id)) {
            throw new DataDeletionRestException(
                    "Элемент не был удалён. Скорее всего это связано с какой-то ошибкой на сервере. Повторите попытку позже"
            );
        }
    }

    private int getId(String pathId) {
        int id;
        try {
            id = Integer.parseInt(pathId);
        } catch (NumberFormatException e) {
            throw new MalformedUrlRestException("Идентификатор \"%s\" не является целым числом".formatted(pathId));
        }
        if (id <= 0) {
            throw new MalformedUrlRestException("Идентификатор \"%s\" не является целым положительным числом".formatted(pathId));
        }
        if (!service.existsById(id)) {
            throw new MalformedUrlRestException("Идентификатор \"%s\" не существует");
        }
        return id;
    }

}

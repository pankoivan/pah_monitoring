package org.pah_monitoring.main.services.other.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.exceptions.rest.validation.RestDataSavingValidationException;
import org.pah_monitoring.main.entities.other.MainAdminContact;
import org.pah_monitoring.main.repositorites.other.MainAdminContactRepository;
import org.pah_monitoring.main.services.other.interfaces.MainAdminContactService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@AllArgsConstructor
@Service
public class MainAdminContactServiceImpl implements MainAdminContactService {

    private final MainAdminContactRepository repository;

    @Override
    public List<MainAdminContact> findAll() {
        return repository.findAll();
    }

    @Override
    public MainAdminContact save(MainAdminContact contact) {
        return repository.save(contact);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void checkBindingResult(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new RestDataSavingValidationException(bindingResultAnyErrorMessage(bindingResult));
        }
    }

    @Override
    public void checkValidityForSaving(MainAdminContact contact) throws RestDataSavingValidationException {
        if (repository.existsByContact(contact.getContact())) {
            throw new RestDataSavingValidationException("Контакт \"%s\" уже существует".formatted(contact.getContact()));
        }
        if (repository.existsByDescription(contact.getDescription())) {
            throw new RestDataSavingValidationException("Контакт с описанием \"%s\" уже существует".formatted(contact.getDescription()));
        }
    }

}

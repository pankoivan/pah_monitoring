package org.pah_monitoring.main.services.other.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.other.MainAdminContact;
import org.pah_monitoring.main.repositorites.other.MainAdminContactRepository;
import org.pah_monitoring.main.services.other.interfaces.MainAdminContactService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

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
    public boolean deleteById(Integer id) {
        repository.deleteById(id);
        return repository.existsById(id);
    }

    @Override
    public boolean isBindingResultHasErrors(BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    @Override
    public boolean isValidForSaving(MainAdminContact contact, BindingResult bindingResult) {
        boolean wereErrors = false;
        if (repository.existsByContact(contact.getContact())) {
            bindingResult.addError(new ObjectError("contactAlreadyExists",
                    "Контакт \"%s\" уже существует".formatted(contact.getContact())));
            wereErrors = true;
        }
        if (repository.existsByDescription(contact.getDescription())) {
            bindingResult.addError(new ObjectError("contactDescriptionAlreadyExists",
                    "Контакт с описанием \"%s\" уже существует".formatted(contact.getDescription())));
            wereErrors = true;
        }
        return wereErrors;
    }

}

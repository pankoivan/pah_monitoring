package org.pah_monitoring.main.services.main.main_admin_contacts.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.QuantityRestrictionConstants;
import org.pah_monitoring.main.dto.in.main_admin_contacts.MainAdminContactSavingDto;
import org.pah_monitoring.main.entities.main.main_admin_contacts.MainAdminContact;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.main_admin_contacts.MainAdminContactRepository;
import org.pah_monitoring.main.services.main.main_admin_contacts.interfaces.MainAdminContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class MainAdminContactServiceImpl implements MainAdminContactService {

    private final MainAdminContactRepository repository;

    @Override
    public List<MainAdminContact> findAll() {
        return repository.findAll();
    }

    @Override
    public MainAdminContact findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Контакта с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public MainAdminContact save(MainAdminContactSavingDto savingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    MainAdminContact
                            .builder()
                            .id(savingDto.getId())
                            .contact(savingDto.getContact())
                            .description(savingDto.getDescription())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }
    }

    @Override
    public void deleteById(Integer id) throws DataDeletionServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new DataDeletionServiceException("Сущность с идентификатором \"%s\" не была удалена".formatted(id), e);
        }
    }

    @Override
    public void checkDataValidityForSaving(MainAdminContactSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        if (isNew(savingDto) && repository.count() == QuantityRestrictionConstants.MAX_NUMBER_OF_MAIN_ADMIN_CONTACTS) {
            throw new DataValidationServiceException("Максимально допустимое число контактов: %s".formatted(
                    QuantityRestrictionConstants.MAX_NUMBER_OF_MAIN_ADMIN_CONTACTS)
            );
        }

        if (existsByContact(savingDto)) {
            throw new DataValidationServiceException("Контакт \"%s\" уже существует".formatted(savingDto.getContact()));
        }

        if (existsByDescription(savingDto)) {
            throw new DataValidationServiceException("Контакт с описанием \"%s\" уже существует".formatted(savingDto.getDescription()));
        }

    }

    private boolean isNew(MainAdminContactSavingDto savingDto) {
        return savingDto.getId() == null || !repository.existsById(savingDto.getId());
    }

    private boolean existsByContact(MainAdminContactSavingDto savingDto) {
        Optional<MainAdminContact> mainAdminContact = repository.findByContact(savingDto.getContact());
        return mainAdminContact.isPresent() && !mainAdminContact.get().getId().equals(savingDto.getId());
    }

    private boolean existsByDescription(MainAdminContactSavingDto savingDto) {
        Optional<MainAdminContact> mainAdminContact = repository.findByDescription(savingDto.getDescription());
        return mainAdminContact.isPresent() && !mainAdminContact.get().getId().equals(savingDto.getId());
    }

}

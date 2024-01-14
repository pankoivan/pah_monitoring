package org.pah_monitoring.main.services.main_admin_contacts.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.constants.QuantityRestrictionConstants;
import org.pah_monitoring.main.entities.dto.saving.main_admin_contacts.MainAdminContactSavingDto;
import org.pah_monitoring.main.entities.main_admin_contacts.MainAdminContact;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.repositorites.main_admin_contacts.MainAdminContactRepository;
import org.pah_monitoring.main.services.main_admin_contacts.interfaces.MainAdminContactService;
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
    public void checkDataValidityForSaving(MainAdminContactSavingDto savingDto, BindingResult bindingResult) throws DataValidationServiceException {
        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (isNew(savingDto) && repository.count() == QuantityRestrictionConstants.MAX_NUMBER_OF_MAIN_ADMIN_CONTACTS) {
            throw new DataValidationServiceException("Максимально допустимое число контактов: %s".formatted(
                    QuantityRestrictionConstants.MAX_NUMBER_OF_MAIN_ADMIN_CONTACTS)
            );
        }
        if (isNew(savingDto) && repository.existsByContact(savingDto.getContact())) {
            throw new DataValidationServiceException("Контакт \"%s\" уже существует".formatted(savingDto.getContact()));
        }
        if (isNew(savingDto) && repository.existsByDescription(savingDto.getDescription())) {
            throw new DataValidationServiceException("Контакт с описанием \"%s\" уже существует".formatted(savingDto.getDescription()));
        }
    }

    @Override
    public int parsePathId(String pathId) throws UrlValidationServiceException {
        int id;
        try {
            id = Integer.parseInt(pathId);
        } catch (NumberFormatException e) {
            throw new UrlValidationServiceException("Идентификатор \"%s\" не является целым числом".formatted(pathId));
        }
        if (id <= 0) {
            throw new UrlValidationServiceException("Идентификатор \"%s\" не является целым положительным числом".formatted(pathId));
        }
        if (!repository.existsById(id)) {
            throw new UrlValidationServiceException("Идентификатор \"%s\" не существует".formatted(pathId));
        }
        return id;
    }

    private boolean isNew(MainAdminContactSavingDto savingDto) {
        return savingDto.getId() == null || !repository.existsById(savingDto.getId());
    }

}

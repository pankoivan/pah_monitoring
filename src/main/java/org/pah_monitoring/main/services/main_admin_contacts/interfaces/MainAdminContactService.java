package org.pah_monitoring.main.services.main_admin_contacts.interfaces;

import org.pah_monitoring.main.entities.dto.saving.main_admin_contacts.MainAdminContactSavingDto;
import org.pah_monitoring.main.entities.main_admin_contacts.MainAdminContact;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataSavingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.url.UrlValidationService;

import java.util.List;

public interface MainAdminContactService extends DataSavingValidationService<MainAdminContactSavingDto>, UrlValidationService {

    List<MainAdminContact> findAll();

    MainAdminContact findById(Integer id) throws DataSearchingServiceException;

    MainAdminContact save(MainAdminContactSavingDto savingDto) throws DataSavingServiceException;

    void deleteById(Integer id) throws DataSearchingServiceException, DataDeletionServiceException;

}

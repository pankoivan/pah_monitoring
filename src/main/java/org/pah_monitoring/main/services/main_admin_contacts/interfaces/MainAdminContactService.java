package org.pah_monitoring.main.services.main_admin_contacts.interfaces;

import org.pah_monitoring.main.entities.main_admin_contacts.MainAdminContact;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.UrlValidationService;

import java.util.List;

public interface MainAdminContactService extends SavingValidationService<MainAdminContact>, UrlValidationService {

    List<MainAdminContact> findAll();

    MainAdminContact save(MainAdminContact contact) throws DataSavingServiceException;

    void deleteById(Integer id) throws DataDeletionServiceException;

}

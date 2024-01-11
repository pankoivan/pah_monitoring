package org.pah_monitoring.main.services.other.interfaces;

import org.pah_monitoring.main.entities.other.MainAdminContact;
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

package org.pah_monitoring.main.services.other.interfaces;

import org.pah_monitoring.main.entities.other.MainAdminContact;
import org.pah_monitoring.main.services.common.interfaces.SavingValidationService;

import java.util.List;

public interface MainAdminContactService extends SavingValidationService<MainAdminContact> {

    List<MainAdminContact> findAll();

    MainAdminContact save(MainAdminContact contact);

    void deleteById(Integer id);

}

package org.pah_monitoring.main.services.other.interfaces;

import org.pah_monitoring.main.entities.other.MainAdminContact;
import org.pah_monitoring.main.services.common.interfaces.SavingValidationService;

import java.util.List;

public interface MainAdminContactService extends SavingValidationService<MainAdminContact> {

    boolean existsById(Integer id);

    List<MainAdminContact> findAll();

    MainAdminContact save(MainAdminContact contact);

    boolean deleteById(Integer id);

}

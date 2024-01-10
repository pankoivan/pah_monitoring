package org.pah_monitoring.main.services.other.interfaces;

import org.pah_monitoring.main.entities.other.MainAdminContact;

import java.util.List;

public interface MainAdminContactService {

    List<MainAdminContact> findAll();

    MainAdminContact save(MainAdminContact contact);

    void deleteById(Integer id);

}

package org.pah_monitoring.main.services.other.implementations;

import org.pah_monitoring.main.repositorites.other.MainAdminContactRepository;
import org.pah_monitoring.main.services.other.interfaces.MainAdminContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainAdminContactServiceImpl implements MainAdminContactService {

    private final MainAdminContactRepository repository;

    @Autowired
    public MainAdminContactServiceImpl(MainAdminContactRepository repository) {
        this.repository = repository;
    }

}

package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.users.users.MainAdministratorRepository;
import org.pah_monitoring.main.services.interfaces.MainAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainAdministratorServiceImpl implements MainAdministratorService {

    private final MainAdministratorRepository repository;

    @Autowired
    public MainAdministratorServiceImpl(MainAdministratorRepository repository) {
        this.repository = repository;
    }

}

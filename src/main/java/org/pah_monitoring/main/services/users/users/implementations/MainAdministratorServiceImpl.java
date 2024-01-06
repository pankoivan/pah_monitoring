package org.pah_monitoring.main.services.users.users.implementations;

import org.pah_monitoring.main.repositorites.users.users.MainAdministratorRepository;
import org.pah_monitoring.main.services.users.users.interfaces.MainAdministratorService;
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

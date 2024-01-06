package org.pah_monitoring.main.services.users.users.implementations;

import org.pah_monitoring.main.repositorites.users.users.AdministratorRepository;
import org.pah_monitoring.main.services.users.users.interfaces.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository repository;

    @Autowired
    public AdministratorServiceImpl(AdministratorRepository repository) {
        this.repository = repository;
    }

}

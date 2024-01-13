package org.pah_monitoring.main.services.users.users.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.MainAdministratorRepository;
import org.pah_monitoring.main.services.users.users.interfaces.MainAdministratorService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MainAdministratorServiceImpl implements MainAdministratorService {

    private final MainAdministratorRepository repository;

}

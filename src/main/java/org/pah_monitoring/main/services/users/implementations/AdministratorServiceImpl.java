package org.pah_monitoring.main.services.users.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.AdministratorRepository;
import org.pah_monitoring.main.services.users.interfaces.AdministratorService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository repository;

}

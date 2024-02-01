package org.pah_monitoring.main.services.main.users.users.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.users.users.MainAdministrator;
import org.pah_monitoring.main.repositorites.main.users.users.MainAdministratorRepository;
import org.pah_monitoring.main.services.main.users.users.interfaces.MainAdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class MainAdministratorServiceImpl implements MainAdministratorService {

    private final MainAdministratorRepository repository;

    @Override
    public MainAdministrator get() {
        return repository.findAll().get(0);
    }

}

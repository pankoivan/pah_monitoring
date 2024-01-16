package org.pah_monitoring.main.services.users.users.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.repositorites.users.MainAdministratorRepository;
import org.pah_monitoring.main.services.users.users.interfaces.MainAdministratorService;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Service
public class MainAdministratorServiceImpl implements MainAdministratorService {

    private MainAdministratorRepository repository;

}

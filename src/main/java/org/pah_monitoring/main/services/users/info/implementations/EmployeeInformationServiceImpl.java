package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.info.EmployeeInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class EmployeeInformationServiceImpl implements EmployeeInformationService {

    private final EmployeeInformationRepository repository;

}

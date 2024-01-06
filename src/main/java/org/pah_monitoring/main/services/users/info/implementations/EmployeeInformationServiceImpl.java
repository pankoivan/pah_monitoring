package org.pah_monitoring.main.services.users.info.implementations;

import org.pah_monitoring.main.repositorites.users.info.EmployeeInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeInformationServiceImpl implements EmployeeInformationService {

    private final EmployeeInformationRepository repository;

    @Autowired
    public EmployeeInformationServiceImpl(EmployeeInformationRepository repository) {
        this.repository = repository;
    }

}

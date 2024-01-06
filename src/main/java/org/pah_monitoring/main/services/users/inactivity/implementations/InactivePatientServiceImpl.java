package org.pah_monitoring.main.services.users.inactivity.implementations;

import org.pah_monitoring.main.repositorites.users.inactivity.InactivePatientRepository;
import org.pah_monitoring.main.services.users.inactivity.interfaces.InactivePatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InactivePatientServiceImpl implements InactivePatientService {

    private final InactivePatientRepository repository;

    @Autowired
    public InactivePatientServiceImpl(InactivePatientRepository repository) {
        this.repository = repository;
    }

}

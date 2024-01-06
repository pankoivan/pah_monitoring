package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.users.inactivity.InactivePatientRepository;
import org.pah_monitoring.main.services.interfaces.PatientRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientRecoveryServiceImpl implements PatientRecoveryService {

    private final InactivePatientRepository repository;

    @Autowired
    public PatientRecoveryServiceImpl(InactivePatientRepository repository) {
        this.repository = repository;
    }

}

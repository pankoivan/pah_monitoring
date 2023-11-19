package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.PatientRecoveryRepository;
import org.pah_monitoring.main.services.interfaces.PatientRecoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientRecoveryServiceImpl implements PatientRecoveryService {

    private final PatientRecoveryRepository repository;

    @Autowired
    public PatientRecoveryServiceImpl(PatientRecoveryRepository repository) {
        this.repository = repository;
    }

}

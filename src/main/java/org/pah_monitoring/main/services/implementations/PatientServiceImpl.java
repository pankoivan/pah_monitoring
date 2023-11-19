package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.PatientRepository;
import org.pah_monitoring.main.services.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    @Autowired
    public PatientServiceImpl(PatientRepository repository) {
        this.repository = repository;
    }

}

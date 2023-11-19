package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.PatientCardRepository;
import org.pah_monitoring.main.services.interfaces.PatientCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientCardServiceImpl implements PatientCardService {

    private final PatientCardRepository repository;

    @Autowired
    public PatientCardServiceImpl(PatientCardRepository repository) {
        this.repository = repository;
    }

}

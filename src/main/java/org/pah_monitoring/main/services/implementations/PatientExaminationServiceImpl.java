package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.PatientExaminationRepository;
import org.pah_monitoring.main.services.interfaces.PatientExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientExaminationServiceImpl implements PatientExaminationService {

    private final PatientExaminationRepository repository;

    @Autowired
    public PatientExaminationServiceImpl(PatientExaminationRepository repository) {
        this.repository = repository;
    }

}

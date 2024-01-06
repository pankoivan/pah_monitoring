package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.examinations.examination.ExaminationRepository;
import org.pah_monitoring.main.services.interfaces.PatientExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientExaminationServiceImpl implements PatientExaminationService {

    private final ExaminationRepository repository;

    @Autowired
    public PatientExaminationServiceImpl(ExaminationRepository repository) {
        this.repository = repository;
    }

}

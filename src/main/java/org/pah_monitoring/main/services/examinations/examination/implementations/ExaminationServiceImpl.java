package org.pah_monitoring.main.services.examinations.examination.implementations;

import org.pah_monitoring.main.repositorites.examinations.examination.ExaminationRepository;
import org.pah_monitoring.main.services.examinations.examination.interfaces.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExaminationServiceImpl implements ExaminationService {

    private final ExaminationRepository repository;

    @Autowired
    public ExaminationServiceImpl(ExaminationRepository repository) {
        this.repository = repository;
    }

}

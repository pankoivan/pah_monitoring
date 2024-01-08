package org.pah_monitoring.main.services.examinations.examination.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.examination.ExaminationRepository;
import org.pah_monitoring.main.services.examinations.examination.interfaces.ExaminationService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExaminationServiceImpl implements ExaminationService {

    private final ExaminationRepository repository;

}

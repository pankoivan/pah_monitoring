package org.pah_monitoring.main.services.examinations.examinations.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.examinations.ExaminationRepository;
import org.pah_monitoring.main.services.examinations.examinations.interfaces.ExaminationService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExaminationServiceImpl implements ExaminationService {

    private final ExaminationRepository repository;

}

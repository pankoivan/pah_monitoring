package org.pah_monitoring.main.services.examinations.schedule.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.schedule.ExaminationScheduleRepository;
import org.pah_monitoring.main.services.examinations.examination.interfaces.ExaminationService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExaminationScheduleServiceImpl implements ExaminationService {

    private final ExaminationScheduleRepository repository;

}

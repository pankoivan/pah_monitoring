package org.pah_monitoring.main.services.examinations.schedules.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.schedules.ExaminationScheduleRepository;
import org.pah_monitoring.main.services.examinations.interfaces.ExaminationService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ExaminationScheduleServiceImpl implements ExaminationService {

    private final ExaminationScheduleRepository repository;

}

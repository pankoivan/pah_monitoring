package org.pah_monitoring.main.services.examinations.schedule.implementations;

import org.pah_monitoring.main.repositorites.examinations.schedule.ExaminationScheduleRepository;
import org.pah_monitoring.main.services.examinations.examination.interfaces.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExaminationScheduleServiceImpl implements ExaminationService {

    private final ExaminationScheduleRepository repository;

    @Autowired
    public ExaminationScheduleServiceImpl(ExaminationScheduleRepository repository) {
        this.repository = repository;
    }

}

package org.pah_monitoring.main.services.examinations.schedules.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.repositorites.examinations.schedules.ExaminationScheduleRepository;
import org.pah_monitoring.main.services.examinations.examinations.interfaces.ExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class ExaminationScheduleServiceImpl implements ExaminationService {

    private final ExaminationScheduleRepository repository;

}

package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.PressureRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.PressureService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PressureServiceImpl implements PressureService {

    private PressureRepository repository;

}

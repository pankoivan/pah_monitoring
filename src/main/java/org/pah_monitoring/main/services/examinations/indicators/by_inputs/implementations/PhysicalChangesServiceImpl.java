package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.PhysicalChangesRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.PhysicalChangesService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PhysicalChangesServiceImpl implements PhysicalChangesService {

    private PhysicalChangesRepository repository;

}

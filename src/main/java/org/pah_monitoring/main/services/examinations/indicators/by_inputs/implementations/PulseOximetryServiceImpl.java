package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.PulseOximetryRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.PulseOximetryService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PulseOximetryServiceImpl implements PulseOximetryService {

    private final PulseOximetryRepository repository;

}

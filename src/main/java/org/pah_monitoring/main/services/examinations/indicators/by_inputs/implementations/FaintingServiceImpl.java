package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.FaintingRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.FaintingService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FaintingServiceImpl implements FaintingService {

    private final FaintingRepository repository;

}

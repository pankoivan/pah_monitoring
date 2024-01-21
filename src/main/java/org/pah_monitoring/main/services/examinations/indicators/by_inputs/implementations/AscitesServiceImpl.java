package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.AscitesRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.AscitesService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AscitesServiceImpl implements AscitesService {

    private final AscitesRepository repository;

}

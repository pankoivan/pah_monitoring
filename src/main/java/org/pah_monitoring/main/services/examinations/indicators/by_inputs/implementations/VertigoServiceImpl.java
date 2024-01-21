package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.VertigoRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.VertigoService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VertigoServiceImpl implements VertigoService {

    private final VertigoRepository repository;

}

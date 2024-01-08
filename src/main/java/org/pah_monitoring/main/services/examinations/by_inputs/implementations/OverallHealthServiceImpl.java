package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.by_inputs.OverallHealthRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.OverallHealthService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class OverallHealthServiceImpl implements OverallHealthService {

    private OverallHealthRepository repository;

}

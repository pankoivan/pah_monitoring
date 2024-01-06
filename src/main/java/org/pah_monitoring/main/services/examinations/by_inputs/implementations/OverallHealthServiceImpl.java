package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.OverallHealthRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.OverallHealthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OverallHealthServiceImpl implements OverallHealthService {

    private OverallHealthRepository repository;

    @Autowired
    public OverallHealthServiceImpl(OverallHealthRepository repository) {
        this.repository = repository;
    }

}

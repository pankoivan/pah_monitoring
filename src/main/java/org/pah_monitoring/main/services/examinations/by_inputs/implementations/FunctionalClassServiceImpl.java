package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.FunctionalClassRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.FunctionalClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FunctionalClassServiceImpl implements FunctionalClassService {

    private final FunctionalClassRepository repository;

    @Autowired
    public FunctionalClassServiceImpl(FunctionalClassRepository repository) {
        this.repository = repository;
    }

}

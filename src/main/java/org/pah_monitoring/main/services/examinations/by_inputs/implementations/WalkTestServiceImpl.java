package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.WalkTestRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.WalkTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalkTestServiceImpl implements WalkTestService {

    private final WalkTestRepository repository;

    @Autowired
    public WalkTestServiceImpl(WalkTestRepository repository) {
        this.repository = repository;
    }

}

package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.FaintingRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.FaintingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FaintingServiceImpl implements FaintingService {

    private final FaintingRepository repository;

    @Autowired
    public FaintingServiceImpl(FaintingRepository repository) {
        this.repository = repository;
    }

}

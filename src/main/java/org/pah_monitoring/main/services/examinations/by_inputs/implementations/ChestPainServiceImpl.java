package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.ChestPainRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.ChestPainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChestPainServiceImpl implements ChestPainService {

    private final ChestPainRepository repository;

    @Autowired
    public ChestPainServiceImpl(ChestPainRepository repository) {
        this.repository = repository;
    }

}

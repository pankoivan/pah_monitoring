package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.CoughRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.CoughService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoughServiceImpl implements CoughService {

    private final CoughRepository repository;

    @Autowired
    public CoughServiceImpl(CoughRepository repository) {
        this.repository = repository;
    }

}

package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.PressureRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.PressureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PressureServiceImpl implements PressureService {

    private PressureRepository repository;

    @Autowired
    public PressureServiceImpl(PressureRepository repository) {
        this.repository = repository;
    }

}

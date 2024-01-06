package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.LiquidAndWeightRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.LiquidAndWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LiquidAndWeightServiceImpl implements LiquidAndWeightService {

    private LiquidAndWeightRepository repository;

    @Autowired
    public LiquidAndWeightServiceImpl(LiquidAndWeightRepository repository) {
        this.repository = repository;
    }

}

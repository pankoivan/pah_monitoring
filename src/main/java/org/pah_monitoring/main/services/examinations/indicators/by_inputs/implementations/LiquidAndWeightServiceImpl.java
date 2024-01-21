package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.LiquidAndWeightRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.LiquidAndWeightService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LiquidAndWeightServiceImpl implements LiquidAndWeightService {

    private LiquidAndWeightRepository repository;

}

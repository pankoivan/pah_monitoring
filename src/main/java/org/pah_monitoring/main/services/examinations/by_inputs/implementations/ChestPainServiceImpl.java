package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.by_inputs.ChestPainRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.ChestPainService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ChestPainServiceImpl implements ChestPainService {

    private final ChestPainRepository repository;

}

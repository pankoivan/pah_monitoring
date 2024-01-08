package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.by_inputs.CoughRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.CoughService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CoughServiceImpl implements CoughService {

    private final CoughRepository repository;

}

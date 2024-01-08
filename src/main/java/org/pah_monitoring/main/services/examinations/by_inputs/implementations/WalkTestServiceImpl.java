package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.by_inputs.WalkTestRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.WalkTestService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class WalkTestServiceImpl implements WalkTestService {

    private final WalkTestRepository repository;

}

package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.by_inputs.FunctionalClassRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.FunctionalClassService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FunctionalClassServiceImpl implements FunctionalClassService {

    private final FunctionalClassRepository repository;

}

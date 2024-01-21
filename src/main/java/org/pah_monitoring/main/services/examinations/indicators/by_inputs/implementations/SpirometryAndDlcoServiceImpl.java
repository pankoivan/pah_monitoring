package org.pah_monitoring.main.services.examinations.indicators.by_inputs.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs.SpirometryAndDlcoRepository;
import org.pah_monitoring.main.services.examinations.indicators.by_inputs.interfaces.SpirometryAndDlcoService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SpirometryAndDlcoServiceImpl implements SpirometryAndDlcoService {

    private final SpirometryAndDlcoRepository repository;

}

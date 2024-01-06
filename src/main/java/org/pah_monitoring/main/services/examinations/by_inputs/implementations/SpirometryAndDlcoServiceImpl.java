package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.SpirometryAndDlcoRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.SpirometryAndDlcoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpirometryAndDlcoServiceImpl implements SpirometryAndDlcoService {

    private final SpirometryAndDlcoRepository repository;

    @Autowired
    public SpirometryAndDlcoServiceImpl(SpirometryAndDlcoRepository repository) {
        this.repository = repository;
    }

}

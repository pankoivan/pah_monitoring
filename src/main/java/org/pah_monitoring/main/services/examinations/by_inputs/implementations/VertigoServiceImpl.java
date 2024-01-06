package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.VertigoRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.VertigoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VertigoServiceImpl implements VertigoService {

    private final VertigoRepository repository;

    @Autowired
    public VertigoServiceImpl(VertigoRepository repository) {
        this.repository = repository;
    }

}

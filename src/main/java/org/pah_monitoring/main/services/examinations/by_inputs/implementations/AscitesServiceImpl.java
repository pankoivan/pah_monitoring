package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.AscitesRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.AscitesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AscitesServiceImpl implements AscitesService {

    private final AscitesRepository repository;

    @Autowired
    public AscitesServiceImpl(AscitesRepository repository) {
        this.repository = repository;
    }

}

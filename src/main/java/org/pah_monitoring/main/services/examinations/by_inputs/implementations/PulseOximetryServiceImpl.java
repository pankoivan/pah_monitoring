package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.PulseOximetryRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.PulseOximetryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PulseOximetryServiceImpl implements PulseOximetryService {

    private final PulseOximetryRepository repository;

    @Autowired
    public PulseOximetryServiceImpl(PulseOximetryRepository repository) {
        this.repository = repository;
    }

}

package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.VacationRepository;
import org.pah_monitoring.main.services.interfaces.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VacationServiceImpl implements VacationService {

    private final VacationRepository repository;

    @Autowired
    public VacationServiceImpl(VacationRepository repository) {
        this.repository = repository;
    }

}

package org.pah_monitoring.main.services.examinations.by_inputs.implementations;

import org.pah_monitoring.main.repositorites.examinations.by_inputs.PhysicalChangesRepository;
import org.pah_monitoring.main.services.examinations.by_inputs.interfaces.PhysicalChangesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhysicalChangesServiceImpl implements PhysicalChangesService {

    private PhysicalChangesRepository repository;

    @Autowired
    public PhysicalChangesServiceImpl(PhysicalChangesRepository repository) {
        this.repository = repository;
    }

}

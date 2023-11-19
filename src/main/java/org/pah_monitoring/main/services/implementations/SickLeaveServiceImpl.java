package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.SickLeaveRepository;
import org.pah_monitoring.main.services.interfaces.SickLeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SickLeaveServiceImpl implements SickLeaveService {

    private final SickLeaveRepository repository;

    @Autowired
    public SickLeaveServiceImpl(SickLeaveRepository repository) {
        this.repository = repository;
    }

}

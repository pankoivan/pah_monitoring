package org.pah_monitoring.main.services.users.inactivity.implementations;

import org.pah_monitoring.main.repositorites.users.inactivity.SickLeaveRepository;
import org.pah_monitoring.main.services.users.inactivity.interfaces.SickLeaveService;
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

package org.pah_monitoring.main.services.users.inactivity.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.inactivity.SickLeaveRepository;
import org.pah_monitoring.main.services.users.inactivity.interfaces.SickLeaveService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class SickLeaveServiceImpl implements SickLeaveService {

    private final SickLeaveRepository repository;

}

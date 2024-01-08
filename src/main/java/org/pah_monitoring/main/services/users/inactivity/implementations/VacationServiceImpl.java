package org.pah_monitoring.main.services.users.inactivity.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.inactivity.VacationRepository;
import org.pah_monitoring.main.services.users.inactivity.interfaces.VacationService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class VacationServiceImpl implements VacationService {

    private final VacationRepository repository;

}

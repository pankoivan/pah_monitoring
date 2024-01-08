package org.pah_monitoring.main.services.users.inactivity.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.inactivity.InactivePatientRepository;
import org.pah_monitoring.main.services.users.inactivity.interfaces.InactivePatientService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class InactivePatientServiceImpl implements InactivePatientService {

    private final InactivePatientRepository repository;

}

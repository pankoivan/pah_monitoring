package org.pah_monitoring.main.services.users.users.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.users.PatientRepository;
import org.pah_monitoring.main.services.users.users.interfaces.PatientService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

}

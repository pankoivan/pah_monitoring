package org.pah_monitoring.main.services.users.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.PatientRepository;
import org.pah_monitoring.main.services.users.interfaces.PatientService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

}

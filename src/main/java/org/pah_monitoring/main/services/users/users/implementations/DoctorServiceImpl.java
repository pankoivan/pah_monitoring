package org.pah_monitoring.main.services.users.users.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.DoctorRepository;
import org.pah_monitoring.main.services.users.users.interfaces.DoctorService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;

}

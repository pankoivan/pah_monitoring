package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.users.users.DoctorRepository;
import org.pah_monitoring.main.services.interfaces.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository repository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository repository) {
        this.repository = repository;
    }

}

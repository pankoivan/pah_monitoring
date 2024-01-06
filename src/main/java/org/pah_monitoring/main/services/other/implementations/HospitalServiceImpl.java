package org.pah_monitoring.main.services.other.implementations;

import org.pah_monitoring.main.repositorites.other.HospitalRepository;
import org.pah_monitoring.main.services.other.interfaces.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository repository;

    @Autowired
    public HospitalServiceImpl(HospitalRepository repository) {
        this.repository = repository;
    }

}

package org.pah_monitoring.main.services.other.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.other.HospitalRepository;
import org.pah_monitoring.main.services.other.interfaces.HospitalService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository repository;

}

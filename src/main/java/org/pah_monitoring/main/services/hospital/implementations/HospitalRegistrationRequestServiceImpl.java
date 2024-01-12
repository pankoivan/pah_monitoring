package org.pah_monitoring.main.services.hospital.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.hospital.HospitalRegistrationRequestRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HospitalRegistrationRequestServiceImpl {

    private final HospitalRegistrationRequestRepository repository;

}

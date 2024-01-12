package org.pah_monitoring.main.services.other.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.other.HospitalRegistrationRequestRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class HospitalRegistrationRequestServiceImpl {

    private final HospitalRegistrationRequestRepository repository;

}

package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.repositorites.hospitals.HospitalRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository repository;

    @Override
    public List<Hospital> findAll() {
        return repository.findAll();
    }

    @Override
    public Hospital save(Hospital hospital) {
        return save(hospital, Hospital.CurrentState.WAITING_CODE, null);
    }

    @Override
    public void register(Hospital hospital) {
        save(hospital, Hospital.CurrentState.REGISTERED, LocalDateTime.now());
    }

    private Hospital save(Hospital hospital, Hospital.CurrentState currentState, LocalDateTime registrationDate) {
        hospital.setCurrentState(currentState);
        hospital.setRegistrationDate(registrationDate);
        return repository.save(hospital);
    }

}

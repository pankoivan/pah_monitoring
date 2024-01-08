package org.pah_monitoring.main.services.other.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.other.Hospital;
import org.pah_monitoring.main.repositorites.other.HospitalRepository;
import org.pah_monitoring.main.services.other.interfaces.HospitalService;
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
        return save(hospital, Hospital.CurrentState.WAITING, null);
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

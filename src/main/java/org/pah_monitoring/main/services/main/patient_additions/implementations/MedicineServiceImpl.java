package org.pah_monitoring.main.services.main.patient_additions.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.main.patient_additions.MedicineRepository;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.MedicineService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository repository;

}

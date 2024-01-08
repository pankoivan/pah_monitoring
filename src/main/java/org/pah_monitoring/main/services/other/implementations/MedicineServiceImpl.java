package org.pah_monitoring.main.services.other.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.other.MedicineRepository;
import org.pah_monitoring.main.services.other.interfaces.MedicineService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository repository;

}

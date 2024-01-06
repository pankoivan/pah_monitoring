package org.pah_monitoring.main.services.other.implementations;

import org.pah_monitoring.main.repositorites.other.MedicineRepository;
import org.pah_monitoring.main.services.other.interfaces.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineServiceImpl implements MedicineService {

    private final MedicineRepository repository;

    @Autowired
    public MedicineServiceImpl(MedicineRepository repository) {
        this.repository = repository;
    }

}

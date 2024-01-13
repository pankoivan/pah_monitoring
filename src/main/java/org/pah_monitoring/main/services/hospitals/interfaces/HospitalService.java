package org.pah_monitoring.main.services.hospitals.interfaces;

import org.pah_monitoring.main.entities.hospitals.Hospital;

import java.util.List;

public interface HospitalService {

    List<Hospital> findAll();

    Hospital save(Hospital hospital);

    void register(Hospital hospital);

}

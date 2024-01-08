package org.pah_monitoring.main.services.other.interfaces;

import org.pah_monitoring.main.entities.other.Hospital;

import java.util.List;

public interface HospitalService {

    List<Hospital> findAll();

    void save(Hospital hospital);

    void register(Hospital hospital);

}

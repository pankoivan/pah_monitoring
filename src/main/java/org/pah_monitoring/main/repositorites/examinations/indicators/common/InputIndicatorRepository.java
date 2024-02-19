package org.pah_monitoring.main.repositorites.examinations.indicators.common;

import org.pah_monitoring.main.entities.main.users.users.Patient;

import java.util.List;

public interface InputIndicatorRepository<T> {

    List<T> findAllByPatientId(Integer id);

    List<InputIndicator> findAllByPatient(Patient patient);

}

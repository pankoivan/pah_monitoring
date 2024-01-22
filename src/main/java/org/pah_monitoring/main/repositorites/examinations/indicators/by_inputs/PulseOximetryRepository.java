package org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs;

import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.PulseOximetry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PulseOximetryRepository extends JpaRepository<PulseOximetry, Integer> {

    List<PulseOximetry> findAllByPatientId(Integer id);

}

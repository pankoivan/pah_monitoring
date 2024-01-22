package org.pah_monitoring.main.repositorites.examinations.indicators.by_inputs;

import org.pah_monitoring.main.entities.examinations.indicators.by_inputs.Ascites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AscitesRepository extends JpaRepository<Ascites, Integer> {

    List<Ascites> findAllByPatientId(Integer id);

}

package org.pah_monitoring.main.repositorites.patient_additions;

import org.pah_monitoring.main.entities.patient_additions.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {

}

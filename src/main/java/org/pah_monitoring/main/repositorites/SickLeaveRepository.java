package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SickLeaveRepository extends JpaRepository<SickLeave, Integer> {

}

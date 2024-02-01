package org.pah_monitoring.main.repositorites.main.users.inactivity;

import org.pah_monitoring.main.entities.main.users.inactivity.SickLeave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SickLeaveRepository extends JpaRepository<SickLeave, Integer> {

    List<SickLeave> findAllByEmployeeId(Integer id);

}

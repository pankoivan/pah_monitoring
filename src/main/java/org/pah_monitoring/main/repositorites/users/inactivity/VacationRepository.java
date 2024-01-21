package org.pah_monitoring.main.repositorites.users.inactivity;

import org.pah_monitoring.main.entities.users.inactivity.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation, Integer> {

    List<Vacation> findAllByEmployeeId(Integer id);

}

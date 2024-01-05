package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeInformationRepository extends JpaRepository<EmployeeInformation, Integer> {

}

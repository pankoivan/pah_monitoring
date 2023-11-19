package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.MainAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainAdministratorRepository extends JpaRepository<MainAdministrator, Integer> {

}

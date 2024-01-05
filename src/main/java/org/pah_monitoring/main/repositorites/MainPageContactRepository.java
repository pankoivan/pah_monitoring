package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.other.MainAdminContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainPageContactRepository extends JpaRepository<MainAdminContact, Integer> {

}

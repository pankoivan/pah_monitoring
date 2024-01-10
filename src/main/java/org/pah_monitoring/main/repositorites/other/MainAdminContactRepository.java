package org.pah_monitoring.main.repositorites.other;

import org.pah_monitoring.main.entities.other.MainAdminContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainAdminContactRepository extends JpaRepository<MainAdminContact, Integer> {

    boolean existsByContact(String contact);

    boolean existsByDescription(String description);

}

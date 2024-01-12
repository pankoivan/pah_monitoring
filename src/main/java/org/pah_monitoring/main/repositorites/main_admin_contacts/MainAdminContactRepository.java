package org.pah_monitoring.main.repositorites.main_admin_contacts;

import org.pah_monitoring.main.entities.main_admin_contacts.MainAdminContact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainAdminContactRepository extends JpaRepository<MainAdminContact, Integer> {

    boolean existsByContact(String contact);

    boolean existsByDescription(String description);

}

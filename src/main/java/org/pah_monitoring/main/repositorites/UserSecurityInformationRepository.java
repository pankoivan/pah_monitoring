package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.UserSecurityInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityInformationRepository extends JpaRepository<UserSecurityInformation, Integer> {

}

package org.pah_monitoring.main.repositorites.users.info;

import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSecurityInformationRepository extends JpaRepository<UserSecurityInformation, Integer> {

    boolean existsByEmail(String email);

    Optional<UserSecurityInformation> findByEmail(String email);

}

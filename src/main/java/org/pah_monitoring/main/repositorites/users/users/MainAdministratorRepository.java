package org.pah_monitoring.main.repositorites.users.users;

import org.pah_monitoring.main.entities.users.users.MainAdministrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MainAdministratorRepository extends JpaRepository<MainAdministrator, Integer> {

    Optional<UserDetails> findByUserSecurityInformationEmail(String email);

}

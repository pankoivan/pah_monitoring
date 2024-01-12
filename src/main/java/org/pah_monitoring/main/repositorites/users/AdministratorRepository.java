package org.pah_monitoring.main.repositorites.users;

import org.pah_monitoring.main.entities.users.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

    Optional<UserDetails> findByUserSecurityInformationEmail(String email);

}

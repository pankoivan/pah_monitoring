package org.pah_monitoring.main.repositorites;

import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationSecurityCodeRepository extends JpaRepository<RegistrationSecurityCode, Integer> {

    Optional<RegistrationSecurityCode> findByCode(UUID code);

}

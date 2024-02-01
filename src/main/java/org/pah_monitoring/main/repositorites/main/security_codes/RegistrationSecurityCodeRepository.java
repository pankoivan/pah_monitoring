package org.pah_monitoring.main.repositorites.main.security_codes;

import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegistrationSecurityCodeRepository extends JpaRepository<RegistrationSecurityCode, Integer> {

    boolean existsByCode(UUID code);

    Optional<RegistrationSecurityCode> findByCode(UUID code);

    boolean existsByEmail(String email);

    Optional<RegistrationSecurityCode> findByEmail(String email);

    void deleteByEmail(String email);

}

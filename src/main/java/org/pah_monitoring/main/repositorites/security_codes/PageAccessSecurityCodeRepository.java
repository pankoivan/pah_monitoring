package org.pah_monitoring.main.repositorites.security_codes;

import org.pah_monitoring.main.entities.security_codes.PageAccessSecurityCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PageAccessSecurityCodeRepository extends JpaRepository<PageAccessSecurityCode, Integer> {

    boolean existsByCode(UUID code);

}

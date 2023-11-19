package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.interfaces.RegistrationSecurityCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationSecurityCodeServiceImpl implements RegistrationSecurityCodeService {

    private final RegistrationSecurityCodeRepository repository;

    @Autowired
    public RegistrationSecurityCodeServiceImpl(RegistrationSecurityCodeRepository repository) {
        this.repository = repository;
    }

}

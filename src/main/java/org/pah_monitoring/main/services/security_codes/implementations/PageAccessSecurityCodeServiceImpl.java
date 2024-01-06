package org.pah_monitoring.main.services.security_codes.implementations;

import org.pah_monitoring.main.repositorites.security_codes.PageAccessSecurityCodeRepository;
import org.pah_monitoring.main.services.security_codes.interfaces.PageAccessSecurityCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageAccessSecurityCodeServiceImpl implements PageAccessSecurityCodeService {

    private final PageAccessSecurityCodeRepository repository;

    @Autowired
    public PageAccessSecurityCodeServiceImpl(PageAccessSecurityCodeRepository repository) {
        this.repository = repository;
    }

}

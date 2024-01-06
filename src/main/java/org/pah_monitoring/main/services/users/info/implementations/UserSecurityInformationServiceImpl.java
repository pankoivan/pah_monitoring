package org.pah_monitoring.main.services.users.info.implementations;

import org.pah_monitoring.main.repositorites.users.info.UserSecurityInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityInformationServiceImpl implements UserSecurityInformationService {

    private final UserSecurityInformationRepository repository;

    @Autowired
    public UserSecurityInformationServiceImpl(UserSecurityInformationRepository repository) {
        this.repository = repository;
    }

}

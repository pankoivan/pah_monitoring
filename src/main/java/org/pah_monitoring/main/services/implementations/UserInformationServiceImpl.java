package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.UserInformationRepository;
import org.pah_monitoring.main.services.interfaces.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInformationServiceImpl implements UserInformationService {

    private final UserInformationRepository repository;

    @Autowired
    public UserInformationServiceImpl(UserInformationRepository repository) {
        this.repository = repository;
    }

}

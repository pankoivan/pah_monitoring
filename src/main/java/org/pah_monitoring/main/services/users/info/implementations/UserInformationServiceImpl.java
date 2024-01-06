package org.pah_monitoring.main.services.users.info.implementations;

import org.pah_monitoring.main.repositorites.users.info.UserInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
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

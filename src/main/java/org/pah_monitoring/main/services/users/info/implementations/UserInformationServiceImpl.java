package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.info.UserInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserInformationServiceImpl implements UserInformationService {

    private final UserInformationRepository repository;

}

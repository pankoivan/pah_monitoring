package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.users.message.UserMessageRepository;
import org.pah_monitoring.main.services.interfaces.UserMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMessageServiceImpl implements UserMessageService {

    private final UserMessageRepository repository;

    @Autowired
    public UserMessageServiceImpl(UserMessageRepository repository) {
        this.repository = repository;
    }

}

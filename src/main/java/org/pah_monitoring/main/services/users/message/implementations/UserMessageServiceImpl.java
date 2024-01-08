package org.pah_monitoring.main.services.users.message.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.message.UserMessageRepository;
import org.pah_monitoring.main.services.users.message.interfaces.UserMessageService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserMessageServiceImpl implements UserMessageService {

    private final UserMessageRepository repository;

}

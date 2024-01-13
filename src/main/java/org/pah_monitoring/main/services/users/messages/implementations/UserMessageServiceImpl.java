package org.pah_monitoring.main.services.users.messages.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.messages.UserMessageRepository;
import org.pah_monitoring.main.services.users.messages.interfaces.UserMessageService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserMessageServiceImpl implements UserMessageService {

    private final UserMessageRepository repository;

}

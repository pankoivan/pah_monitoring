package org.pah_monitoring.main.services.users.inactivity.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.users.inactivity.DismissalRepository;
import org.pah_monitoring.main.services.users.inactivity.interfaces.DismissalService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DismissalServiceImpl implements DismissalService {

    private final DismissalRepository repository;

}

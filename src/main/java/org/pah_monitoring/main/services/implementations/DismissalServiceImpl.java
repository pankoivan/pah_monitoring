package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.DismissalRepository;
import org.pah_monitoring.main.services.interfaces.DismissalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DismissalServiceImpl implements DismissalService {

    private final DismissalRepository repository;

    @Autowired
    public DismissalServiceImpl(DismissalRepository repository) {
        this.repository = repository;
    }

}

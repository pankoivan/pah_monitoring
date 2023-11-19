package org.pah_monitoring.main.services.implementations;

import org.pah_monitoring.main.repositorites.MainPageContactRepository;
import org.pah_monitoring.main.services.interfaces.MainPageContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainPageContactServiceImpl implements MainPageContactService {

    private final MainPageContactRepository repository;

    @Autowired
    public MainPageContactServiceImpl(MainPageContactRepository repository) {
        this.repository = repository;
    }

}

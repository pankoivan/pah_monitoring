package org.pah_monitoring.main.services.other.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.other.MainAdminContact;
import org.pah_monitoring.main.repositorites.other.MainAdminContactRepository;
import org.pah_monitoring.main.services.other.interfaces.MainAdminContactService;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class MainAdminContactServiceImpl implements MainAdminContactService {

    private final MainAdminContactRepository repository;

    @Override
    public List<MainAdminContact> findAll() {
        return repository.findAll();
    }

    @Override
    public MainAdminContact save(MainAdminContact contact) {
        return repository.save(contact);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

}

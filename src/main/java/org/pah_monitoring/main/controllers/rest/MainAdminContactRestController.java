package org.pah_monitoring.main.controllers.rest;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.other.MainAdminContact;
import org.pah_monitoring.main.services.other.interfaces.MainAdminContactService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/contacts")
@PreAuthorize("permitAll()") // todo: only main admin
public class MainAdminContactRestController {

    private final MainAdminContactService service;

    @GetMapping
    public List<MainAdminContact> get() {
        return service.findAll();
    }

    @PostMapping
    public MainAdminContact add(@RequestBody MainAdminContact contact) {
        return null; // todo
    }

    @PutMapping
    public MainAdminContact edit(@RequestBody MainAdminContact contact) {
        return null; // todo
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        // todo: check if id is correct, and convert it from String to real id (Integer)
    }

}

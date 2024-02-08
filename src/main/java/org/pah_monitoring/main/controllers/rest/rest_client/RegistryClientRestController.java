package org.pah_monitoring.main.controllers.rest.rest_client;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.additional.rest.client.RegistryHospital;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.RestClientRestControllerException;
import org.pah_monitoring.main.exceptions.service.rest_client.RestClientServiceException;
import org.pah_monitoring.main.services.additional.rest_client.interfaces.RegistryRestClientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/rest/client/registry")
@PreAuthorize("permitAll()")
public class RegistryClientRestController {

    private final RegistryRestClientService service;

    @PostMapping("/search")
    public List<RegistryHospital> search(@RequestBody String search) {
        try {
            return service.search(search);
        } catch (RestClientServiceException e) {
            throw new RestClientRestControllerException(e.getMessage(), e);
        }
    }

}

package org.pah_monitoring.main.controllers.rest.rest_client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.additional.rest_client.RegistryHospital;
import org.pah_monitoring.main.exceptions.controller.rest.bad_request.RestClientRestControllerException;
import org.pah_monitoring.main.exceptions.service.rest_client.RestClientServiceException;
import org.pah_monitoring.main.services.additional.rest_client.interfaces.RegistryRestClientService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/client/registry")
public class RegistryClientRestController {

    private final RegistryRestClientService service;

    @PostMapping("/search")
    public List<RegistryHospital> search(@RequestBody SearchRequest searchRequest) {
        try {
            return service.search(searchRequest.search);
        } catch (RestClientServiceException e) {
            throw new RestClientRestControllerException(e.getMessage(), e);
        }
    }

    @NoArgsConstructor @AllArgsConstructor @Data
    public static class SearchRequest {
        String search;
    }

}

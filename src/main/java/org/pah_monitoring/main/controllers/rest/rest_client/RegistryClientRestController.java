package org.pah_monitoring.main.controllers.rest.rest_client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.rest_client.RegistryHospital;
import org.pah_monitoring.main.exceptions.service.rest_client.RestClientServiceException;
import org.pah_monitoring.main.services.auxiliary.rest_client.interfaces.RegistryRestClientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/client/registry")
public class RegistryClientRestController {

    private final RegistryRestClientService service;

    @PostMapping("/search")
    public List<RegistryHospital> search(@RequestBody SearchRequest searchRequest) throws RestClientServiceException {
        return service.search(searchRequest.search);
    }

    @NoArgsConstructor @AllArgsConstructor @Data
    public static class SearchRequest {
        String search;
    }

}

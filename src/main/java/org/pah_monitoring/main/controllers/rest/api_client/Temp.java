package org.pah_monitoring.main.controllers.rest.api_client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.entities.api_client.RegistryHospital;
import org.pah_monitoring.main.exceptions.service.api_client.ApiClientServiceException;
import org.pah_monitoring.main.exceptions.utils.UrlUtilsException;
import org.pah_monitoring.main.services.auxiliary.rest_client.implementations.RegistryRestClientServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/byb")
public class Temp {

    private final RegistryRestClientServiceImpl service;

    /*@GetMapping
    public List<Byb> get() {
        return IntStream.range(0, 10).mapToObj(i -> new Byb("Hello" + i)).toList();
    }*/

    @GetMapping
    public List<RegistryHospital> get() throws ApiClientServiceException {
        return service.search("Алм");
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    public static class Byb {
        String bybString;
    }

}

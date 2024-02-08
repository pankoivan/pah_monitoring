package org.pah_monitoring.main.services.additional.rest_client.implementations;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.pah_monitoring.auxiliary.utils.UrlUtils;
import org.pah_monitoring.main.entities.additional.rest_client.RegistryHospital;
import org.pah_monitoring.main.exceptions.service.rest_client.RestClientServiceException;
import org.pah_monitoring.main.exceptions.utils.UrlUtilsException;
import org.pah_monitoring.main.services.additional.rest_client.interfaces.RegistryRestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class RegistryRestClientServiceImpl implements RegistryRestClientService {

    private final RestTemplate restTemplate;

    @Value("${my.registry-api.base-url}")
    private final String baseUrl;

    @Value("${my.registry-api.registry-identifier}")
    private final String identifier;

    @Value("${my.registry-api.token}")
    private final String token;

    @Override
    public List<RegistryHospital> search(String search) throws RestClientServiceException {
        return search(search, 15);
    }

    @Override
    public List<RegistryHospital> search(String search, int limit) throws RestClientServiceException {
        return getRegistryHospitalSet(search, "LIKE")
                .stream()
                .map(this::toResponseRegistryHospital)
                .limit(limit)
                .toList();
    }

    @Override
    public Optional<RegistryHospital> selected(String selected) throws RestClientServiceException {
        return getRegistryHospitalSet(selected, "EXACT")
                .stream()
                .map(this::toResponseRegistryHospital)
                .findFirst();
    }

    private RegistryHospital toResponseRegistryHospital(Set<Pair> registryHospital) {
        RegistryHospital responseRegistryHospital = new RegistryHospital();
        for (Pair pair : registryHospital) {
            if (pair.isNameFull()) {
                responseRegistryHospital.setName(pair.value);
            }
            if (pair.isOid()) {
                responseRegistryHospital.setOid(pair.value);
            }
        }
        return responseRegistryHospital;
    }

    /*private Set<Set<Pair>> getRegistryHospitalSetForAllSearchCombinations(String searched, String mode) throws RestClientServiceException {
        Set<Set<Pair>> set = new HashSet<>();
        set.addAll(getRegistryHospitalSet(searched.toLowerCase(), mode));
        set.addAll(getRegistryHospitalSet(searched.toUpperCase(), mode));
        set.addAll(getRegistryHospitalSet(
                searched.substring(0, 1).toUpperCase() + searched.substring(1).toLowerCase(), mode
        ));
        System.out.println(set);
        System.out.println("--------------------------------------------------SIZE: " + set.size());
        return set;
    }*/

    private Set<Set<Pair>> getRegistryHospitalSet(String searched, String mode) throws RestClientServiceException {
        Set<Set<Pair>> set = new HashSet<>();
        /*int total = getBaseResponse(1, 1, searched, mode).total;
        for (int i = 1; set.size() < total; ++i) {
            set.addAll(getBaseResponse(200, i, searched, mode).set);
        }*/
        set.addAll(getBaseResponse(4, 1, searched.toLowerCase(), mode).set);
        set.addAll(getBaseResponse(4, 1, searched.toUpperCase(), mode).set);
        if (searched.length() > 1) {
            set.addAll(getBaseResponse(3, 1,
                    searched.substring(0, 1).toUpperCase() + searched.substring(1).toLowerCase(), mode).set
            );
        }
        System.out.println(set);
        System.out.println("--------------------------------------------------SIZE: " + set.size());
        return set;
    }

    private BaseResponse getBaseResponse(Integer onPage, Integer page, String searched, String mode) throws RestClientServiceException {
        try {
            return restTemplate.exchange(
                    RequestEntity.get(UrlUtils.buildUrlWithGetParameters(
                                    baseUrl,
                                    "identifier", identifier,
                                    "userKey", token,
                                    "size", onPage,
                                    "page", page,
                                    "filters", "nameFull|%s|%s".formatted(searched, mode),
                                    "columns", "nameFull",
                                    "columns", "oid"
                            )
                    ).build(),
                    BaseResponse.class
            ).getBody();
        } catch (UrlUtilsException | RestClientException e) {
            throw new RestClientServiceException("Произошла ошибка при REST-взаимодействии с API реестра", e);
        }
    }

    @NoArgsConstructor @AllArgsConstructor @Data
    private static class BaseResponse {
        private Integer total;
        @JsonProperty("list")
        private Set<Set<Pair>> set;
    }

    @NoArgsConstructor @AllArgsConstructor @Data
    private static class Pair {
        String column;
        String value;
        public boolean isNameFull() {
            return column.equals("nameFull");
        }
        public boolean isOid() {
            return column.equals("oid");
        }
    }

}

package org.pah_monitoring.main.services.additional.rest_client.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.UrlUtils;
import org.pah_monitoring.main.entities.additional.rest.client.Pair;
import org.pah_monitoring.main.entities.additional.rest.client.RegistryBaseResponse;
import org.pah_monitoring.main.entities.additional.rest.client.RegistryHospital;
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
                .map(this::toRegistryHospital)
                .limit(limit)
                .toList();
    }

    @Override
    public Optional<RegistryHospital> selected(String selected) throws RestClientServiceException {
        return getRegistryHospitalSet(selected, "EXACT")
                .stream()
                .map(this::toRegistryHospital)
                .findFirst();
    }

    private RegistryHospital toRegistryHospital(List<Pair> registryHospital) {
        RegistryHospital responseRegistryHospital = new RegistryHospital();
        for (Pair pair : registryHospital) {
            if (pair.isNameFull()) {
                responseRegistryHospital.setName(pair.getValue());
            }
            if (pair.isOid()) {
                responseRegistryHospital.setOid(pair.getValue());
            }
        }
        return responseRegistryHospital;
    }

    private Set<List<Pair>> getRegistryHospitalSet(String searched, String mode) throws RestClientServiceException {
        Set<List<Pair>> set = new HashSet<>();
        set.addAll(getRegistryBaseResponse(4, 1, searched.toLowerCase(), mode).getRegistryHospitalSet());
        set.addAll(getRegistryBaseResponse(4, 1, searched.toUpperCase(), mode).getRegistryHospitalSet());
        if (searched.length() > 1) {
            set.addAll(getRegistryBaseResponse(3, 1,
                    searched.substring(0, 1).toUpperCase() + searched.substring(1).toLowerCase(), mode).getRegistryHospitalSet()
            );
        }
        return set;
    }

    private RegistryBaseResponse getRegistryBaseResponse(Integer onPage, Integer page, String searched, String mode)
            throws RestClientServiceException {
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
                    RegistryBaseResponse.class
            ).getBody();
        } catch (UrlUtilsException | RestClientException e) {
            throw new RestClientServiceException("Произошла ошибка при REST-взаимодействии с API реестра", e);
        }
    }

}

package org.pah_monitoring.main.services.additional.rest_client.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.QuantityRestrictionConstants;
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
        return getRegistryHospitalSet(search)
                .stream()
                .limit(QuantityRestrictionConstants.MAX_NUMBER_OF_REGISTRY_HOSPITALS)
                .map(this::toRegistryHospital)
                .toList();
    }

    @Override
    public Optional<RegistryHospital> select(String select) throws RestClientServiceException {
        return getRegistryHospital(select).map(this::toRegistryHospital);
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

    private Set<List<Pair>> getRegistryHospitalSet(String searched) throws RestClientServiceException {
        searched = searched == null ? "" : searched;
        Set<List<Pair>> set = new HashSet<>();
        set.addAll(getRegistryBaseResponse("nameFull", searched, "LIKE").getRegistryHospitalSet());
        set.addAll(getRegistryBaseResponse("nameFull", searched.toLowerCase(), "LIKE").getRegistryHospitalSet());
        set.addAll(getRegistryBaseResponse("nameFull", searched.toUpperCase(), "LIKE").getRegistryHospitalSet());
        if (searched.length() > 1) {
            set.addAll(
                    getRegistryBaseResponse("nameFull", searched.substring(0, 1).toUpperCase() + searched.substring(1).toLowerCase(), "LIKE")
                            .getRegistryHospitalSet()
            );
        }
        return set;
    }

    private Optional<List<Pair>> getRegistryHospital(String select) throws RestClientServiceException {
        select = select == null ? "" : select;
        Set<List<Pair>> found = getRegistryBaseResponse("oid", select, "EXACT").getRegistryHospitalSet();
        if (found == null) {
            return Optional.empty();
        } else {
            return found.stream().findFirst();
        }
    }

    private RegistryBaseResponse getRegistryBaseResponse(String field, String search, String mode) throws RestClientServiceException {
        try {
            return restTemplate.exchange(
                    RequestEntity.get(UrlUtils.buildUrlWithGetParameters(
                                    baseUrl,
                                    "identifier", identifier,
                                    "userKey", token,
                                    "page", 1,
                                    "size", QuantityRestrictionConstants.MAX_NUMBER_OF_REGISTRY_HOSPITALS,
                                    "filters", "%s|%s|%s".formatted(field, search, mode),
                                    "columns", "nameFull",
                                    "columns", "oid"
                            )
                    ).build(),
                    RegistryBaseResponse.class
            ).getBody();
        } catch (UrlUtilsException | RestClientException e) {
            throw new RestClientServiceException("Произошла ошибка при REST-взаимодействии с API реестра медицинских организаций РФ", e);
        }
    }

}

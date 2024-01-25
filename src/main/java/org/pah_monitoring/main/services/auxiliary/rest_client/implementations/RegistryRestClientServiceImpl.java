package org.pah_monitoring.main.services.auxiliary.rest_client.implementations;

import lombok.*;
import org.pah_monitoring.auxiliary.utils.UrlUtils;
import org.pah_monitoring.main.entities.api_client.RegistryHospital;
import org.pah_monitoring.main.exceptions.service.api_client.RestClientServiceException;
import org.pah_monitoring.main.exceptions.utils.UrlUtilsException;
import org.pah_monitoring.main.services.auxiliary.rest_client.interfaces.RegistryRestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

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

    public List<RegistryHospital> search(String search) throws RestClientServiceException {

        return getRegistryHospitalList()
                .stream()
                .filter(rh -> matches(search, rh))
                .map(this::toResponseRegistryHospital)
                .toList();

    }

    private RegistryHospital toResponseRegistryHospital(List<Pair> registryHospital) {

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

    private boolean matches(String search, List<Pair> registryHospital) {

        for (Pair pair : registryHospital) {
            if (pair.isNameFull() && pair.value.contains(search)) {
                return true;
            }
        }
        return false;

    }

    private List<List<Pair>> getRegistryHospitalList() throws RestClientServiceException {

        List<List<Pair>> list = new ArrayList<>();

        int total = getBaseResponse(1, 1).total;
        for (int i = 1; list.size() < total; ++i) {
            list.addAll(getBaseResponse(200, i).list);
        }

        return list;
    }

    private BaseResponse getBaseResponse(Integer onPage, Integer page) throws RestClientServiceException {

        try {

            RequestEntity<Void> request = RequestEntity
                    .get(UrlUtils.buildUrlWithGetParameters(
                            baseUrl,
                            "identifier", identifier,
                            "userKey", token,
                            "size", onPage,
                            "page", page
                    ))
                    .build();

            return restTemplate.exchange(request, BaseResponse.class).getBody();

        } catch (UrlUtilsException | RestClientException e) {
            throw new RestClientServiceException("Произошла ошибка при REST-взаимодействии с API реестра", e);
        }

    }

    @NoArgsConstructor @AllArgsConstructor @Data
    private static class BaseResponse {
        private Integer total;
        private List<List<Pair>> list;
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

package org.pah_monitoring.main.services.auxiliary.rest_client.implementations;

import lombok.*;
import org.pah_monitoring.auxiliary.utils.UrlUtils;
import org.pah_monitoring.main.exceptions.service.api_client.ApiClientServiceException;
import org.pah_monitoring.main.exceptions.utils.UrlUtilsException;
import org.pah_monitoring.main.services.auxiliary.rest_client.interfaces.RegistryRestClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
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

    private BaseResponse getBaseResponse(Integer page, Integer onPage) throws ApiClientServiceException {

        try {

            RequestEntity<Void> request = RequestEntity
                    .get(UrlUtils.buildUrlWithGetParameters(
                            baseUrl,
                            "identifier", identifier,
                            "userKey", token,
                            "page", page,
                            "size", onPage
                    ))
                    .build();

            return restTemplate.exchange(request, BaseResponse.class).getBody();

        } catch (UrlUtilsException | RestClientException e) {
            throw new ApiClientServiceException("Произошла ошибка при REST-взаимодействии с API реестра", e);
        }

    }

    @NoArgsConstructor @AllArgsConstructor @Data
    private static class BaseResponse {
        private Integer total;
        private List<List<HashMap<String, String>>> list;
    }

}

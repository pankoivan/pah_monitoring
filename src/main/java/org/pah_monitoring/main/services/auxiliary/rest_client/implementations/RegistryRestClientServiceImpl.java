package org.pah_monitoring.main.services.auxiliary.rest_client.implementations;

import lombok.*;
import org.pah_monitoring.auxiliary.utils.UrlUtils;
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

    private BaseResponse exchange(Integer page, Integer onPage) throws UrlUtilsException {

        RequestEntity<Void> request = RequestEntity
                .get(UrlUtils.buildUrlWithGetParameters(baseUrl, "identifier", identifier, "userKey", token))
                .build();

        try {
            return restTemplate.exchange(request, BaseResponse.class).getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }

    }

    @NoArgsConstructor @AllArgsConstructor @Data
    private static class BaseResponse {
        private Integer total;
        private List<List<HashMap<String, String>>> list;
    }

}

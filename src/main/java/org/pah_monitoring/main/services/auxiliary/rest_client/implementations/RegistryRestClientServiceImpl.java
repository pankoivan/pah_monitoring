package org.pah_monitoring.main.services.auxiliary.rest_client.implementations;

import lombok.*;
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

    @Value("${my.registry-api.token}")
    private final String token;

    public Response exchange() {

        RequestEntity<Void> request = RequestEntity
                .get(baseUrl + "&userKey=" + token)
                //.header("userKey", token)
                .build();

        System.out.println(baseUrl);
        System.out.println(token);

        try {
            return restTemplate.exchange(request, Response.class).getBody();
        } catch (RestClientException e) {
            e.printStackTrace();
            return null;
        }

    }

    @NoArgsConstructor @AllArgsConstructor @Data
    public static class Response {
        private List<List<HashMap<String, String>>> list;
    }

}

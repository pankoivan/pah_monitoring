package org.pah_monitoring.main.services.additional.rest_client.interfaces;

import org.pah_monitoring.main.entities.additional.rest.client.RegistryHospital;
import org.pah_monitoring.main.exceptions.service.rest_client.RestClientServiceException;

import java.util.List;
import java.util.Optional;

public interface RegistryRestClientService {

    List<RegistryHospital> search(String search) throws RestClientServiceException;

    Optional<RegistryHospital> select(String select) throws RestClientServiceException;

}

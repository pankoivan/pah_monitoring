package org.pah_monitoring.main.services.additional.rest_client.interfaces;

import org.pah_monitoring.main.entities.additional.rest_client.RegistryHospital;
import org.pah_monitoring.main.exceptions.service.rest_client.RestClientServiceException;

import java.util.List;
import java.util.Optional;

public interface RegistryRestClientService {

    List<RegistryHospital> search(String search) throws RestClientServiceException;

    List<RegistryHospital> search(String search, int limit) throws RestClientServiceException;

    Optional<RegistryHospital> selected(String selected) throws RestClientServiceException;

}

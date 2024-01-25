package org.pah_monitoring.main.services.auxiliary.rest_client.interfaces;

import org.pah_monitoring.main.entities.rest_client.RegistryHospital;
import org.pah_monitoring.main.exceptions.service.rest_client.RestClientServiceException;

import java.util.List;
import java.util.Optional;

public interface RegistryRestClientService {

    Optional<RegistryHospital> selected(String selected) throws RestClientServiceException;

    List<RegistryHospital> search(String search) throws RestClientServiceException;

    List<RegistryHospital> search(String search, int limit) throws RestClientServiceException;

}

package org.pah_monitoring.main.services.auxiliary.rest_client.interfaces;

import org.pah_monitoring.main.entities.api_client.RegistryHospital;
import org.pah_monitoring.main.exceptions.service.api_client.RestClientServiceException;

import java.util.List;

public interface RegistryRestClientService {

    public List<RegistryHospital> search(String search) throws RestClientServiceException;

}

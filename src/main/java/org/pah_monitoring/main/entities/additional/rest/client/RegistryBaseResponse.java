package org.pah_monitoring.main.entities.additional.rest.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class RegistryBaseResponse {

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("list")
    private Set<List<Pair>> registryHospitalSet;

}

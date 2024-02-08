package org.pah_monitoring.main.entities.additional.rest.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Set;

@Data
public class BaseResponse {

    private Integer total;

    @JsonProperty("list")
    private Set<Set<Pair>> set;

}

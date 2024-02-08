package org.pah_monitoring.main.entities.additional.rest.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Pair {

    @JsonProperty("column")
    private String column;

    @JsonProperty("value")
    private String value;

    public boolean isNameFull() {
        return column.equals("nameFull");
    }

    public boolean isOid() {
        return column.equals("oid");
    }

}

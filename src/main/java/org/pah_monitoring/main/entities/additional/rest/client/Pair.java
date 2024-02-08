package org.pah_monitoring.main.entities.additional.rest.client;

import lombok.Data;

@Data
public class Pair {

    String column;

    String value;

    public boolean isNameFull() {
        return column.equals("nameFull");
    }

    public boolean isOid() {
        return column.equals("oid");
    }

}

package org.pah_monitoring.main.services.security_codes.interfaces.common;

import java.util.UUID;

public interface SecurityCodeService<T> {

    void save(T code);

    boolean existsByCode(UUID code);

}

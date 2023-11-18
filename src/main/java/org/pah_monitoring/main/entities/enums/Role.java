package org.pah_monitoring.main.entities.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Role implements GrantedAuthority {

    MAIN_ADMINISTRATOR("Главный администратор"),

    ADMINISTRATOR("Администратор"),

    DOCTOR("Доктор"),

    PATIENT("Пациент");

    private final String alias;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

}

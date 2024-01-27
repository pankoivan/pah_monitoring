package org.pah_monitoring.main.entities.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum Role implements GrantedAuthority {

    MAIN_ADMINISTRATOR("Главный администратор"),

    ADMINISTRATOR("Администратор"),

    DOCTOR("Врач"),

    PATIENT("Пациент");

    private final String alias;

    public static List<Role> hospitalRoles() {
        return List.of(ADMINISTRATOR, DOCTOR, PATIENT);
    }

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }

}

package org.pah_monitoring.main.entities.main.users.users;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@Entity
@Table(name = "main_administrator")
public class MainAdministrator implements User, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_security_information_id")
    private UserSecurityInformation userSecurityInformation;

    @OneToOne
    @JoinColumn(name = "user_information_id")
    private UserInformation userInformation;

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public boolean isNotActive() {
        return false;
    }

    @Override
    public boolean isHospitalUser() {
        return false;
    }

    @Override
    public boolean isHospitalEmployee() {
        return false;
    }

    @Override
    public boolean isMainAdministrator() {
        return true;
    }

    @Override
    public boolean isAdministrator() {
        return false;
    }

    @Override
    public boolean isDoctor() {
        return false;
    }

    @Override
    public boolean isPatient() {
        return false;
    }

    @Override
    public Role getRole() {
        return Role.MAIN_ADMINISTRATOR;
    }

    @Override
    public String getUsername() {
        return userSecurityInformation.getEmail();
    }

    @Override
    public String getPassword() {
        return userSecurityInformation.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(getRole());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof MainAdministrator other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

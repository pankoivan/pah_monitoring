package org.pah_monitoring.main.entities.users.users;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.inactivity.Dismissal;
import org.pah_monitoring.main.entities.users.inactivity.SickLeave;
import org.pah_monitoring.main.entities.users.inactivity.Vacation;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalEmployee;
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
@JsonIncludeProperties({
        "id",
        "userSecurityInformation",
        "employeeInformation",
        "userInformation"
})
@Entity
@Table(name = "administrator")
public class Administrator extends HospitalEmployee implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_security_information_id")
    private UserSecurityInformation userSecurityInformation;

    @OneToOne
    @JoinColumn(name = "hospital_employee_information_id")
    private EmployeeInformation employeeInformation;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(mappedBy = "author")
    private List<Vacation> assignedVacations;

    @OneToMany(mappedBy = "author")
    private List<SickLeave> assignedSickLeaves;

    @OneToMany(mappedBy = "author")
    private List<Dismissal> assignedDismissals;

    @Override
    public boolean isHospitalUser() {
        return true;
    }

    @Override
    public boolean isHospitalEmployee() {
        return true;
    }

    @Override
    public boolean isMainAdministrator() {
        return false;
    }

    @Override
    public boolean isAdministrator() {
        return true;
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
        return Role.ADMINISTRATOR;
    }

    @Override
    public UserInformation getUserInformation() {
        return employeeInformation.getUserInformation();
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
        return !isDismissed();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isDismissed();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !isDismissed();
    }

    @Override
    public boolean isEnabled() {
        return !isDismissed();
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Administrator other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

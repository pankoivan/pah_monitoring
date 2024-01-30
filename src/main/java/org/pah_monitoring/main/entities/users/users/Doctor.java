package org.pah_monitoring.main.entities.users.users;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.inactivity.PatientInactivity;
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
@Table(name = "doctor")
public class Doctor implements HospitalEmployee, UserDetails {

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

    @OneToMany(mappedBy = "doctor")
    private List<Patient> patients;

    @OneToMany(mappedBy = "author")
    private List<PatientInactivity> assignedPatientInactivities;

    public int patientsCount() {
        return patients.size();
    }

    public boolean hasNoPatients() {
        return patients.isEmpty();
    }

    public boolean isActive() {
        return isNotDismissed() && !later1 && !later2;
    }

    public boolean isOnVacation() {
        return later1;
    }

    public boolean isOnSickLeave() {
        return later2;
    }

    public boolean isDismissed() {
        return employeeInformation.getDismissal() != null;
    }

    public boolean isNotDismissed() {
        return !isDismissed();
    }

    public Role getRole() {
        return Role.DOCTOR;
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
        return isNotDismissed();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isNotDismissed();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isNotDismissed();
    }

    @Override
    public boolean isEnabled() {
        return isNotDismissed();
    }

    @Override
    public UserInformation getUserInformation() {
        return employeeInformation.getUserInformation();
    }

    @Override
    public boolean isEmployee() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Doctor other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

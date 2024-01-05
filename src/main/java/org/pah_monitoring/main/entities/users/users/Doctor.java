package org.pah_monitoring.main.entities.users.users;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.examinations.examination.Examination;
import org.pah_monitoring.main.entities.users.inactivity.InactivePatient;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.entities.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "hospitalId", "university"})
@Builder
@Entity
@Table(name = "doctor")
public class Doctor implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "hospital_id")
    private String hospitalId;

    @Column(name = "university")
    private String university;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_security_information_id")
    private UserSecurityInformation userSecurityInformation;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "hospital_employee_information_id")
    private EmployeeInformation employeeInformation;

    @OneToMany(mappedBy = "doctor")
    private List<Patient> patients;

    @OneToMany(mappedBy = "doctor")
    private List<Examination> examinations;

    @OneToMany(mappedBy = "doctor")
    private List<InactivePatient> patientRecoveries;

    public Role getRole() {
        return Role.DOCTOR;
    }

    public boolean isActive() {
        return employeeInformation.getDismissal() == null;
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
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
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

package org.pah_monitoring.main.entities.main.users.users;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;
import org.pah_monitoring.main.entities.main.users.inactivity.PatientInactivity;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalEmployee;
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
public class Doctor extends HospitalEmployee implements UserDetails {

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

    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    public void removePatient(Patient patient) {
        patients.remove(patient);
    }

    public int patientsCount() {
        return patients.size();
    }

    public boolean hasPatients() {
        return !patients.isEmpty();
    }

    public boolean hasNoPatients() {
        return !hasPatients();
    }

    public boolean hasPatientById(Integer id) {
        return !patients.isEmpty() && patients.stream().map(Patient::getId).toList().contains(id);
    }

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
        return false;
    }

    @Override
    public boolean isDoctor() {
        return true;
    }

    @Override
    public boolean isPatient() {
        return false;
    }

    @Override
    public Role getRole() {
        return Role.DOCTOR;
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
                || ((o instanceof Doctor other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

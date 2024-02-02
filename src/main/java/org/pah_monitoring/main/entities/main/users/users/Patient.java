package org.pah_monitoring.main.entities.main.users.users;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.examinations.schedules.ExaminationSchedule;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.entities.main.patient_additions.Medicine;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;
import org.pah_monitoring.main.entities.main.users.inactivity.PatientInactivity;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@JsonIncludeProperties({"id", "userSecurityInformation", "userInformation"})
@Entity
@Table(name = "patient")
public class Patient extends HospitalUser implements UserDetails {

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

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToMany(mappedBy = "patient")
    private List<ExaminationSchedule> schedules;

    @OneToMany(mappedBy = "patient")
    private List<Medicine> medicines;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "patient_achievement",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "achievement_id")
    )
    private List<Achievement> achievements;

    @OneToOne(mappedBy = "patient")
    private PatientInactivity patientInactivity;

    public int getAchievementsCount() {
        return achievements.size();
    }

    public boolean hasDoctor() {
        return doctor != null;
    }

    public boolean hasNoDoctor() {
        return !hasDoctor();
    }

    public Optional<PatientInactivity> getCurrentPatientInactivity() {
        return Optional.ofNullable(patientInactivity);
    }

    @Override
    public boolean isActive() {
        return patientInactivity == null;
    }

    @Override
    public boolean isNotActive() {
        return !isActive();
    }

    @Override
    public String getActivityMessage() {
        if (patientInactivity != null) {
            return "Переведён в неактивное состояние %s".formatted(patientInactivity.getDate());
        } else {
            return "Активен";
        }
    }

    @Override
    public boolean isHospitalUser() {
        return true;
    }

    @Override
    public boolean isHospitalEmployee() {
        return false;
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
        return false;
    }

    @Override
    public boolean isPatient() {
        return true;
    }

    @Override
    public Role getRole() {
        return Role.PATIENT;
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
                || ((o instanceof Patient other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

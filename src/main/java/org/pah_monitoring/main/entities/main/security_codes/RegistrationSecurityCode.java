package org.pah_monitoring.main.entities.main.security_codes;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@Entity
@Table(name = "registration_security_code")
public class RegistrationSecurityCode implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "email")
    private String email;

    @Column(name = "code")
    private UUID code;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    public String getFormattedExpirationDate() {
        return DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(expirationDate);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expirationDate);
    }

    public boolean isNotSuitableForRole(Role role) {
        return this.role != role;
    }

    public boolean isNotSuitableForEmail(String email) {
        return !this.email.equals(email);
    }

    public boolean isForHospitalEmployee() {
        return role == Role.ADMINISTRATOR || role == Role.DOCTOR;
    }

    public boolean isForHospitalThatWaitingRegistration() {
        return hospital.getCurrentState() == Hospital.CurrentState.WAITING_REGISTRATION;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof RegistrationSecurityCode other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

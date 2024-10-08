package org.pah_monitoring.main.entities.main.hospitals;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@Entity
@Table(name = "hospital")
public class Hospital implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "oid")
    private String oid;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_state")
    private CurrentState currentState;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToMany(mappedBy = "hospital")
    private List<Administrator> administrators;

    @OneToMany(mappedBy = "hospital")
    private List<Doctor> doctors;

    @OneToMany(mappedBy = "hospital")
    private List<Patient> patients;

    @OneToOne(mappedBy = "hospital")
    private HospitalRegistrationRequest request;

    public boolean isWaitingCode() {
        return currentState == CurrentState.WAITING_CODE;
    }

    public boolean isWaitingRegistration() {
        return currentState == CurrentState.WAITING_REGISTRATION;
    }

    public boolean isRegistered() {
        return currentState == CurrentState.REGISTERED;
    }

    public String getFormattedDate() {
        return DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE__READABLE.format(date);
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Hospital other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum CurrentState {

        WAITING_CODE("Ожидает выдачи кода"),

        WAITING_REGISTRATION("Ожидает регистрации администратора"),

        REGISTERED("Зарегистрировано");

        private final String alias;

    }

}

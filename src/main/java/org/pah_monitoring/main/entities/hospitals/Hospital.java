package org.pah_monitoring.main.entities.hospitals;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.users.Patient;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"employees", "patients"})
@Builder
@Entity
@Table(name = "hospital")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "oid")
    private String oid;

    @Column(name = "name")
    private String name;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_state")
    private CurrentState currentState;

    @OneToMany(mappedBy = "hospital")
    private List<EmployeeInformation> employees;

    @OneToMany(mappedBy = "hospital")
    private List<Patient> patients;

    @OneToOne(mappedBy = "hospital", cascade = CascadeType.REMOVE)
    private HospitalRegistrationRequest hospitalRegistrationRequest;

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

        WAITING_CODE("Ожидает выдачу кода"),

        WAITING_REGISTRATION("Ожидает регистрацию администратора"),

        REGISTERED("Зарегистрировано");

        private final String alias;

    }

}

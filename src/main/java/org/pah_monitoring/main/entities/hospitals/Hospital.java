package org.pah_monitoring.main.entities.hospitals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.users.users.Administrator;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.Patient;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {
        "administrators",
        "doctors",
        "patients",
        "request"
})
@Builder
@JsonIgnoreProperties({
        "administrators",
        "doctors",
        "patients",
        "request"
})
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

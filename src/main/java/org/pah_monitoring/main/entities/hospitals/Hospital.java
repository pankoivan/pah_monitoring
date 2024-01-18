package org.pah_monitoring.main.entities.hospitals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"employees", "patients", "request"})
@Builder
@JsonIgnoreProperties({"employees", "patients", "request"})
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

    @Column(name = "date")
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_state")
    private CurrentState currentState;

    /*@OneToMany(mappedBy = "hospital")
    private List<EmployeeInformation> employees;

    @OneToMany(mappedBy = "hospital")
    private List<Patient> patients;*/

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

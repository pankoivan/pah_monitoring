package org.pah_monitoring.main.entities.other;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.examinations.by_inputs.Ascites;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.users.users.Patient;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"employees", "patients"})
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

    @Column(name = "current_state")
    private HospitalCurrentState currentState;

    @OneToMany(mappedBy = "hospital")
    private List<EmployeeInformation> employees;

    @OneToMany(mappedBy = "hospital")
    private List<Patient> patients;


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

}

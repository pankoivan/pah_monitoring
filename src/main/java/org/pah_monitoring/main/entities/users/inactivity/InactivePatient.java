package org.pah_monitoring.main.entities.users.inactivity;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.users.users.Doctor;
import org.pah_monitoring.main.entities.users.users.Patient;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Entity
@Table(name = "recovery")
public class InactivePatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Doctor doctor;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof InactivePatient other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

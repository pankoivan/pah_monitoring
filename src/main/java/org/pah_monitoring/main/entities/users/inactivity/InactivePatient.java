package org.pah_monitoring.main.entities.users.inactivity;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.users.Doctor;
import org.pah_monitoring.main.entities.users.Patient;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"patient", "doctor"})
@Builder
@Entity
@Table(name = "inactive_patient")
public class InactivePatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "comment")
    private String comment;

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

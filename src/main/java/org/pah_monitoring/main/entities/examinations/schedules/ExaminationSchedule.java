package org.pah_monitoring.main.entities.examinations.schedules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.enums.IndicatorsGroup;
import org.pah_monitoring.main.entities.users.users.Patient;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "patient")
@Builder
@JsonIgnoreProperties("patient")
@Entity
@Table(name = "examination_schedule")
public class ExaminationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "indicators_group")
    private IndicatorsGroup indicatorsGroup;

    @Column(name = "schedule")
    private String schedule;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ExaminationSchedule other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

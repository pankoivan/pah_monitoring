package org.pah_monitoring.main.entities.examinations.schedule;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.users.users.Patient;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "patient")
@Builder
@Entity
@Table(name = "examination_schedule")
public class ExaminationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "indicators_group")
    private IndicatorsGroup indicatorsGroup;

    @Column(name = "times")
    private ScheduleTimes times;

    @Column(name = "period")
    private SchedulePeriod period;

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

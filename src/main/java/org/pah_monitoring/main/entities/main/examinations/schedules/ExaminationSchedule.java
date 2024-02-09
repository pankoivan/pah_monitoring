package org.pah_monitoring.main.entities.main.examinations.schedules;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.users.users.Patient;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@Entity
@Table(name = "examination_schedule")
public class ExaminationSchedule implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "indicators_group")
    private IndicatorType indicatorType;

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

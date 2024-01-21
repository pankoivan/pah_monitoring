package org.pah_monitoring.main.entities.examinations.indicators.by_inputs;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.enums.EventDuration;
import org.pah_monitoring.main.entities.enums.IndicatorGroup;
import org.pah_monitoring.main.entities.examinations.examinations.Examination;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.Indicator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "examination")
@Builder
@Entity
@Table(name = "vertigo")
public class Vertigo implements Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "duration")
    private EventDuration duration;

    @Column(name = "nausea")
    private Boolean nausea;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Vertigo other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public IndicatorGroup getIndicatorGroup() {
        return IndicatorGroup.VERTIGO;
    }

}

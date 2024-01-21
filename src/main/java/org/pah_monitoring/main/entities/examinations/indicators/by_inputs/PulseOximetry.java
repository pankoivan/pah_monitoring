package org.pah_monitoring.main.entities.examinations.indicators.by_inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.enums.IndicatorGroup;
import org.pah_monitoring.main.entities.examinations.examinations.Examination;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.Indicator;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "examination")
@Builder
@JsonIgnoreProperties("examination")
@Entity
@Table(name = "pulse_oximetry")
public class PulseOximetry implements Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "oxygen_percentage")
    private Double oxygenPercentage;

    @Column(name = "pulse_rate")
    private Integer pulseRate;

    @Column(name = "during_exercise")
    private Boolean duringExercise;

    @ManyToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof PulseOximetry other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public IndicatorGroup getIndicatorGroup() {
        return IndicatorGroup.PULSE_OXIMETRY;
    }

}

package org.pah_monitoring.main.entities.main.examinations.indicators;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "patient")
@Builder
@JsonIgnoreProperties("patient")
@Entity
@Table(name = "physical_changes")
public class PhysicalChanges implements InputIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "acrocyanosis")
    private Boolean acrocyanosis;

    @Column(name = "fingers_phalanges")
    private Boolean fingersPhalanges;

    @Column(name = "nails")
    private Boolean nails;

    @Column(name = "chest")
    private Boolean chest;

    @Column(name = "neck_veins")
    private Boolean neckVeins;

    @Column(name = "lower_extremities")
    private Boolean lowerExtremities;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof PhysicalChanges other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public IndicatorType getIndicatorGroup() {
        return IndicatorType.PHYSICAL_CHANGES;
    }

}

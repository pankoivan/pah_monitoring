package org.pah_monitoring.main.entities.examinations.by_inputs;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.examinations.examination.Examination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "examination")
@Builder
@Entity
@Table(name = "overall_health")
public class OverallHealth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "fatigue")
    private Boolean fatigue;

    @Column(name = "rest_feeling")
    private Boolean restFeeling;

    @Column(name = "drowsiness")
    private Boolean drowsiness;

    @Column(name = "concentration")
    private Boolean concentration;

    @Column(name = "weakness")
    private Boolean weakness;

    @Column(name = "appetite")
    private Boolean appetite;

    @Column(name = "cold_extremities")
    private OverallHealthColdExtremities coldExtremities;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof OverallHealth other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

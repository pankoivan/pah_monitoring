package org.pah_monitoring.main.entities.examinations.by_inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.examinations.Examination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "examination")
@Builder
@JsonIgnoreProperties("examination")
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
    private Weakness weakness;

    @Column(name = "appetite")
    private Boolean appetite;

    @Enumerated(EnumType.STRING)
    @Column(name = "cold_extremities")
    private ColdExtremities coldExtremities;

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

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum Weakness {

        GENERALIZED("Генерализированный вид"),

        ACUTE("Острый вид"),

        RECURRENT("Рецидивирующая");

        private final String alias;

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum ColdExtremities {

        SHORT_TIME("Короткое время"),

        LONG_TIME("Длительное время"),

        CONSTANTLY("Постоянно");

        private final String alias;

    }

}

package org.pah_monitoring.main.entities.main.examinations.indicators;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.TablesInputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@Entity
@Table(name = "overall_health")
public class OverallHealth implements TablesInputIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "breathlessness")
    private Conditions breathlessness;

    @Enumerated(EnumType.STRING)
    @Column(name = "fatigue")
    private Conditions fatigue;

    @Column(name = "rest_feeling")
    private Boolean restFeeling;

    @Column(name = "drowsiness")
    private Boolean drowsiness;

    @Column(name = "concentration")
    private Boolean concentration;

    @Enumerated(EnumType.STRING)
    @Column(name = "weakness")
    private Weakness weakness;

    @Column(name = "appetite")
    private Boolean appetite;

    @Enumerated(EnumType.STRING)
    @Column(name = "cold_extremities")
    private ColdExtremities coldExtremities;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

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

    @Override
    public IndicatorType getIndicatorGroup() {
        return IndicatorType.OVERALL_HEALTH;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum Conditions {

        AT_REST("В покое"),

        NORMAL_PHYSICAL_ACTIVITY("При обычной физической нагрузке"),

        INCREASED_PHYSICAL_ACTIVITY("При повышенной физической нагрузке"),

        NO("Нет");

        private final String alias;

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

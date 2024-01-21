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
@ToString(exclude = {
        "pulseOximetryBefore",
        "pulseOximetryAfter",
        "pressureBefore",
        "pressureAfter",
        "examination",
})
@Builder
@JsonIgnoreProperties({
        "pulseOximetryBefore",
        "pulseOximetryAfter",
        "pressureBefore",
        "pressureAfter",
        "examination",
})
@Entity
@Table(name = "walk_test")
public class WalkTest implements Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "oxygen_support")
    private Boolean oxygenSupport;

    @Column(name = "auxiliary_devices")
    private Boolean auxiliaryDevices;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "number_of_stops")
    private Integer numberOfStops;

    @Enumerated(EnumType.STRING)
    @Column(name = "breathlessness")
    private Breathlessness breathlessness;

    @OneToOne
    @JoinColumn(name = "pulse_oximetry_id_before")
    private PulseOximetry pulseOximetryBefore;

    @OneToOne
    @JoinColumn(name = "pulse_oximetry_id_after")
    private PulseOximetry pulseOximetryAfter;

    @OneToOne
    @JoinColumn(name = "pressure_id_before")
    private Pressure pressureBefore;

    @OneToOne
    @JoinColumn(name = "pressure_id_after")
    private Pressure pressureAfter;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof WalkTest other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public IndicatorGroup getIndicatorGroup() {
        return IndicatorGroup.WALK_TEST;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum Breathlessness {

        SCORE_0("Отсутствует"),

        SCORE_0_5("Очень-очень лёгкая"),

        SCORE_1("Очень лёгкая"),

        SCORE_2("Лёгкая"),

        SCORE_3("Умеренная"),

        SCORE_4("В некоторой степени тяжёлая"),

        SCORE_5("Тяжёлая"),

        SCORE_6("Тяжёлая"),

        SCORE_7("Очень тяжёлая"),

        SCORE_8("Очень тяжёлая"),

        SCORE_9("Очень-очень тяжёлая"),

        SCORE_10("Максимальная");

        private final String alias;

    }

}

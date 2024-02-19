package org.pah_monitoring.main.entities.main.examinations.indicators;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.Indicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
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

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

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
    public IndicatorType getIndicatorType() {
        return IndicatorType.WALK_TEST;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum Breathlessness {

        SCORE_0("Отсутствует (0)"),

        SCORE_0_5("Очень-очень лёгкая (0.5)"),

        SCORE_1("Очень лёгкая (1)"),

        SCORE_2("Лёгкая (2)"),

        SCORE_3("Умеренная (3)"),

        SCORE_4("В некоторой степени тяжёлая (4)"),

        SCORE_5("Тяжёлая (5)"),

        SCORE_6("Тяжёлая (6)"),

        SCORE_7("Очень тяжёлая (7)"),

        SCORE_8("Очень тяжёлая (8)"),

        SCORE_9("Очень-очень тяжёлая (9)"),

        SCORE_10("Максимальная (10)");

        private final String alias;

    }

}

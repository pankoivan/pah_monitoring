package org.pah_monitoring.main.entities.examinations.schedules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.enums.IndicatorsGroup;
import org.pah_monitoring.main.entities.users.users.Patient;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "patient")
@Builder
@JsonIgnoreProperties("patient")
@Entity
@Table(name = "examination_schedule")
public class ExaminationSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "indicators_group")
    private IndicatorsGroup indicatorsGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "times")
    private Times times;

    @Enumerated(EnumType.STRING)
    @Column(name = "period")
    private Period period;

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

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum Times {

        ONE("Один раз"),

        TWO("Два раза"),

        THREE("Три раза"),

        FOURTH("Четыре раза"),

        FIVE("Пять раз");

        private final String alias;

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum Period {

        DAY("В день"),

        TWO_DAYS("В два дня"),

        THREE_DAYS("В три дня"),

        WEEK("В неделю"),

        TWO_WEEKS("В две недели"),

        MONTH("В месяц"),

        TWO_MONTHS("В два месяца"),

        THREE_MONTHS("В три месяца"),

        HALF_YEAR("В полгода"),

        YEAR("В год");

        private final String alias;

    }

}

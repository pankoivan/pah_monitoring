package org.pah_monitoring.main.entities.examinations.indicators.by_inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties("examination")
@Entity
@Table(name = "chest_pain")
public class ChestPain implements Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "duration")
    private EventDuration duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "nitroglycerin")
    private Nitroglycerin nitroglycerin;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof ChestPain other))
                &&(id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public IndicatorGroup getIndicatorGroup() {
        return IndicatorGroup.CHEST_PAIN;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum Type {

        PRESSING("Давящая"),

        ACHING("Ноющая"),

        STABBING("Колющая"),

        COMPRESSING("Сжимающая");

        private final String alias;

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum Nitroglycerin {

        YES("Да"),

        NO("Нет"),

        DID_NOT_TAKE("Не принимал");

        private final String alias;

    }

}

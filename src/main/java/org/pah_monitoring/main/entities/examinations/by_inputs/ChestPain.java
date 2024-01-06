package org.pah_monitoring.main.entities.examinations.by_inputs;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.enums.EventDuration;
import org.pah_monitoring.main.entities.examinations.examination.Examination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "examination")
@Builder
@Entity
@Table(name = "chest_pain")
public class ChestPain {

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

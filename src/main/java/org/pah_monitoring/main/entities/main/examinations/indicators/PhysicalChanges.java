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
@Table(name = "physical_changes")
public class PhysicalChanges implements Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "abdominal_enlargement")
    private Boolean abdominalEnlargement;

    @Enumerated(EnumType.STRING)
    @Column(name = "legs_swelling")
    private LegsSwelling legsSwelling;

    @Column(name = "vascular_asterisks")
    private Boolean vascularAsterisks;

    @Enumerated(EnumType.STRING)
    @Column(name = "skin_color")
    private SkinColor skinColor;

    @Column(name = "fingers_phalanges")
    private Boolean fingersPhalanges;

    @Column(name = "chest")
    private Boolean chest;

    @Column(name = "neck_veins")
    private Boolean neckVeins;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum LegsSwelling {

        MORNING("Утром"),

        EVENING("К вечеру"),

        CONSTANTLY("Постоянно");

        private final String alias;

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum SkinColor {

        ORDINARY("Обычный"),

        PALE("Бледный"),

        BLUISH("Синюшный"),

        YELLOWISH("Желтоватый");

        private final String alias;

    }

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
    public IndicatorType getIndicatorType() {
        return IndicatorType.PHYSICAL_CHANGES;
    }

}

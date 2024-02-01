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
@Table(name = "functional_class")
public class FunctionalClass implements InputIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "functional_class")
    private FunctionalClassNumber functionalClassNumber;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof FunctionalClass other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public IndicatorType getIndicatorGroup() {
        return IndicatorType.FUNCTIONAL_CLASS;
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum FunctionalClassNumber {

        I("ФК 1"),

        II("ФК 2"),

        III("ФК 3"),

        IV("ФК 4");

        private final String alias;

    }

}

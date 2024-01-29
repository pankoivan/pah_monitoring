package org.pah_monitoring.main.entities.examinations.indicators;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.examinations.indicators.common.interfaces.Indicator;
import org.pah_monitoring.main.entities.users.users.Patient;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "patient")
@Builder
@JsonIgnoreProperties("patient")
@Entity
@Table(name = "analysis_file")
public class AnalysisFile implements Indicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "filename")
    private String filename;

    @Enumerated(EnumType.STRING)
    @Column(name = "analysis_type")
    private AnalysisType analysisType;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof AnalysisFile other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /*@Override
    public IndicatorType getIndicatorGroup() {
        return analysisType != null
                ? IndicatorType.valueOf(analysisType.name())
                : null;
    }*/

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum AnalysisType {

        BLOOD_TEST("Развёрнутый анализ крови"),

        ELECTROCARDIOGRAPHY("Электрокардиография"),

        RADIOGRAPHY("Рентгенография органов грудной клетки"),

        ECHOCARDIOGRAPHY("Эхокардиография"),

        COMPUTED_TOMOGRAPHY("Компьютерная томография органов грудной клетки"),

        CATHETERIZATION("Катетеризация правых отделов сердца");

        private final String alias;

    }

}

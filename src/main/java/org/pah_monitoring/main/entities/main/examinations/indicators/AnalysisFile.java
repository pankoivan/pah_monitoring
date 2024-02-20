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

    @Override
    public IndicatorType getIndicatorType() {
        return IndicatorType.valueOf(analysisType.name());
    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum AnalysisType {

        BLOOD_TEST(
                "Развёрнутый анализ крови",
                "blood-test.jpg",
                "/patients/%s/examinations/blood-test",
                "/indicators/blood-test"
        ),

        ELECTROCARDIOGRAPHY(
                "Электрокардиография",
                "electrocardiography.jpg",
                "/patients/%s/examinations/electrocardiography",
                "/indicators/electrocardiography"
        ),

        RADIOGRAPHY(
                "Рентгенография органов грудной клетки",
                "radiography.jpg",
                "/patients/%s/examinations/radiography",
                "/indicators/radiography"
        ),

        ECHOCARDIOGRAPHY(
                "Эхокардиография",
                "echocardiography.jpg",
                "/patients/%s/examinations/echocardiography",
                "/indicators/echocardiography"
        ),

        COMPUTED_TOMOGRAPHY(
                "Компьютерная томография органов грудной клетки",
                "computed-tomography.jpg",
                "/patients/%s/examinations/computed-tomography",
                "/indicators/computed-tomography"
        ),

        SCINTIGRAPHY(
                "Сцинтиграфия лёгких",
                "scintigraphy.jpg",
                "/patients/%s/examinations/scintigraphy",
                "/indicators/scintigraphy"
        ),

        CATHETERIZATION(
                "Катетеризация правых отделов сердца",
                "catheterization.jpg",
                "/patients/%s/examinations/catheterization",
                "/indicators/catheterization"
        );

        private final String name;

        private final String filename;

        private final String fileLink;

        private final String postFormLink;

    }

}

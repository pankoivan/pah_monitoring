package org.pah_monitoring.main.entities.examinations.by_files;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.examinations.Examination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "examination")
@Builder
@Entity
@Table(name = "analysis_file")
public class AnalysisFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "filename")
    private String filename;

    @Enumerated(EnumType.STRING)
    @Column(name = "analysis_type")
    private AnalysisType analysisType;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

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

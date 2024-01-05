package org.pah_monitoring.main.entities.examinations.by_inputs;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.examinations.examination.Examination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "examination")
@Entity
@Table(name = "fainting")
public class Fainting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "duration")
    private CommonDuration duration;

    @Column(name = "during_exercise")
    private Boolean during_exercise;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Fainting other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

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
@Table(name = "pressure")
public class Pressure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "upper")
    private Integer upper;

    @Column(name = "lower")
    private Integer lower;

    @Column(name = "during_exercise")
    private Boolean duringExercise;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Pressure other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

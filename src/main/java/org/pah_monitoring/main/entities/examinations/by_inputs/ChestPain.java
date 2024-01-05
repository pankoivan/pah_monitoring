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
@Table(name = "chest_pain")
public class ChestPain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    private ChestPainType type;

    @Column(name = "duration")
    private CommonDuration duration;

    @Column(name = "nitroglycerin")
    private ChestPainNitroglycerin nitroglycerin;

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

}

package org.pah_monitoring.main.entities.examinations.by_inputs;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.examinations.examination.Examination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "examination")
@Builder
@Entity
@Table(name = "cough")
public class Cough {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CoughType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "power")
    private CoughPower power;

    @Enumerated(EnumType.STRING)
    @Column(name = "timbre")
    private CoughTimbre timbre;

    @Column(name = "hemoptysis")
    private Boolean hemoptysis;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Cough other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

package org.pah_monitoring.main.entities.examinations.by_inputs;

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
@Table(name = "physical_changes")
public class PhysicalChanges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "acrocyanosis")
    private Boolean acrocyanosis;

    @Column(name = "fingers_phalanges")
    private Boolean fingersPhalanges;

    @Column(name = "nails")
    private Boolean nails;

    @Column(name = "chest")
    private Boolean chest;

    @Column(name = "neck_veins")
    private Boolean neckVeins;

    @Column(name = "lower_extremities")
    private Boolean lowerExtremities;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

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

}

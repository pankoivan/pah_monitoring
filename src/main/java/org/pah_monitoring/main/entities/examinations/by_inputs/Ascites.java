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
@Table(name = "ascites")
public class Ascites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "liquid_amount")
    private AscitesLiquidAmount liquidAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_infection")
    private AscitesContentInfection contentInfection;

    @Enumerated(EnumType.STRING)
    @Column(name = "response_to_drug_therapy")
    private AscitesResponseToDrugTherapy responseToDrugTherapy;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Ascites other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

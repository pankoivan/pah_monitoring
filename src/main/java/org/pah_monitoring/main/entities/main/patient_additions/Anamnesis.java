package org.pah_monitoring.main.entities.main.patient_additions;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.users.users.Patient;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@Entity
@Table(name = "anamnesis")
public class Anamnesis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "heart_disease")
    private Boolean heartDisease;

    @Column(name = "lung_disease")
    private Boolean lungDisease;

    @Column(name = "relatives_diseases")
    private Boolean relativesDiseases;

    @Column(name = "blood_clotting")
    private Anamnesis.BloodClotting bloodClotting;

    @Column(name = "diabetes")
    private Boolean diabetes;

    @Column(name = "height")
    private Integer height;

    @Column(name = "weight")
    private Double weight;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum BloodClotting {

        BRUISES("Легко образуются синяки"),

        BLOOD_DOES_NOT_STOP("Кровь долго не останавливается"),

        THROMBOPHLEBITIS("Тромбофлебит");

        private final String alias;

    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Anamnesis other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

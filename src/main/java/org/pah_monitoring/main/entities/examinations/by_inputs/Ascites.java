package org.pah_monitoring.main.entities.examinations.by_inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.examinations.Examination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "examination")
@Builder
@JsonIgnoreProperties("examination")
@Entity
@Table(name = "ascites")
public class Ascites {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "liquid_amount")
    private LiquidAmount liquidAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_infection")
    private ContentInfection contentInfection;

    @Enumerated(EnumType.STRING)
    @Column(name = "response_to_drug_therapy")
    private ResponseToDrugTherapy responseToDrugTherapy;

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

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum LiquidAmount {

        SMALL("Небольшое"),

        MODERATE("Умеренное"),

        SIGNIFICANT("Значительное");

        private final String alias;

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum ContentInfection {

        STERILE("Стерильное"),

        INFECTED("Инфицированное"),

        PERITONITIS("Спонтанный перитонит");

        private final String alias;

    }

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public enum ResponseToDrugTherapy {

        AMENABLE("Поддающийся"),

        CANNOT_BE_ELIMINATED("Не может быть устранён");

        private final String alias;

    }

}

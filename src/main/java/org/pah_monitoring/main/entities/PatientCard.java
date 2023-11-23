package org.pah_monitoring.main.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "doctor")
@Entity
@Table(name = "patient_card")
public class PatientCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "examination_date")
    private LocalDate examinationDate;

    @Column(name = "breathlessness")
    private String breathlessness;

    @Column(name = "fatigue")
    private String fatigue;

    @Column(name = "weakness")
    private String weakness;

    @Column(name = "vertigo")
    private String vertigo;

    @Column(name = "edema")
    private String edema;

    @Column(name = "hepatomegaly")
    private String hepatomegaly;

    @Column(name = "ascites")
    private String ascites;

    @Column(name = "pulsation")
    private String pulsation;

    @Column(name = "chest_pain")
    private String chestPain;

    @Column(name = "hemoptysis")
    private String hemoptysis;

    @Column(name = "pulse_oximetry")
    private String pulseOximetry;

    @Column(name = "angina")
    private String angina;

    @Column(name = "cough")
    private String cough;

    @Column(name = "changes_in_fingers")
    private String changesInFingers;

    @Column(name = "appetite")
    private String appetite;

    @Column(name = "blood_test_link")
    private String bloodTestLink;

    @Column(name = "electrocardiography_link")
    private String electrocardiographyLink;

    @Column(name = "radiography_link")
    private String radiographyLink;

    @Column(name = "echocardiography_link")
    private String echocardiographyLink;

    @Column(name = "chest_tomography_link")
    private String chestTomographyLink;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof PatientCard other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

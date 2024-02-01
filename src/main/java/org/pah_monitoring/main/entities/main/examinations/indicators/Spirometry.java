package org.pah_monitoring.main.entities.main.examinations.indicators;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.enums.IndicatorType;
import org.pah_monitoring.main.entities.main.examinations.indicators.common.interfaces.InputIndicator;
import org.pah_monitoring.main.entities.main.users.users.Patient;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "patient")
@Builder
@JsonIgnoreProperties("patient")
@Entity
@Table(name = "spirometry")
public class Spirometry implements InputIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "vcl")
    private Double vlc;

    @Column(name = "avlc")
    private Double avlc;

    @Column(name = "rlv")
    private Double rlv;

    @Column(name = "vfe1")
    private Double vfe1;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Spirometry other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public IndicatorType getIndicatorGroup() {
        return IndicatorType.SPIROMETRY;
    }

}

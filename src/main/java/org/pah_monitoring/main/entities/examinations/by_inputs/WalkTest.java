package org.pah_monitoring.main.entities.examinations.by_inputs;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.examinations.examination.Examination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {
        "pulseOximetryBefore",
        "pulseOximetryAfter",
        "pressureBefore",
        "pressureAfter",
        "examination",
})
@Builder
@Entity
@Table(name = "examination")
public class WalkTest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "oxygen_support")
    private Boolean oxygenSupport;

    @Column(name = "auxiliary_devices")
    private Boolean auxiliaryDevices;

    @Column(name = "distance")
    private Double distance;

    @Column(name = "number_of_stops")
    private Integer numberOfStops;

    @Column(name = "breathlessness")
    private Breathlessness breathlessness;

    @OneToOne
    @JoinColumn(name = "pulse_oximetry_id_before")
    private PulseOximetry pulseOximetryBefore;

    @OneToOne
    @JoinColumn(name = "pulse_oximetry_id_after")
    private PulseOximetry pulseOximetryAfter;

    @OneToOne
    @JoinColumn(name = "pressure_id_before")
    private Pressure pressureBefore;

    @OneToOne
    @JoinColumn(name = "pressure_id_after")
    private Pressure pressureAfter;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof WalkTest other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

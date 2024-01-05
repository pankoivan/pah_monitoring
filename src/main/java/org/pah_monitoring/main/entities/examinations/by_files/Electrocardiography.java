package org.pah_monitoring.main.entities.examinations.by_files;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.examinations.examination.Examination;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "examination")
@Entity
@Table(name = "electrocardiography")
public class Electrocardiography {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "filename")
    private String filename;

    @OneToOne
    @JoinColumn(name = "examination_id")
    private Examination examination;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Electrocardiography other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

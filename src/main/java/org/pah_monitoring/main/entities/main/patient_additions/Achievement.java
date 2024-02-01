package org.pah_monitoring.main.entities.main.patient_additions;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "achievement")
public class Achievement implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "filename")
    private String filename;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Achievement other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

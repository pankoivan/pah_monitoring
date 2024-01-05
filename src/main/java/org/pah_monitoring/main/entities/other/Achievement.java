package org.pah_monitoring.main.entities.other;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "achievement")
public class Achievement {

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

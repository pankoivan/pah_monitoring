package org.pah_monitoring.main.entities.main.main_admin_contacts;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@Entity
@Table(name = "main_admin_contact")
public class MainAdminContact implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "contact")
    private String contact;

    @Column(name = "description")
    private String description;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof MainAdminContact other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

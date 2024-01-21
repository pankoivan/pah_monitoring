package org.pah_monitoring.main.entities.users.info;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.common.interfaces.BaseEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "user_security_information")
public class UserSecurityInformation implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof UserSecurityInformation other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

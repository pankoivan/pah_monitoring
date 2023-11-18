package org.pah_monitoring.main.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "role")
@Entity
@Table(name = "generated_registration_security_code")
public class RegistrationSecurityCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private UUID code;

    @Column(name = "hospital_id")
    private String hospitalId;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @ManyToOne
    @JoinColumn(name = "user_role_id")
    private Role role;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof RegistrationSecurityCode other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

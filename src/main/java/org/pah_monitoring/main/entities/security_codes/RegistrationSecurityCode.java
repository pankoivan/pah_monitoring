package org.pah_monitoring.main.entities.security_codes;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.enums.ExpirationDate;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.hospital.Hospital;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "hospital")
@Builder
@Entity
@Table(name = "registration_security_code")
public class RegistrationSecurityCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private UUID code;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Transient
    private transient ExpirationDate expirationDateEnum;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

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

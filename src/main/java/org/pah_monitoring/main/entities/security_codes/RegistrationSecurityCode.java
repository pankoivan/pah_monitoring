package org.pah_monitoring.main.entities.security_codes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.hospitals.Hospital;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "hospital")
@Builder
@JsonIgnoreProperties("hospital")
@Entity
@Table(name = "registration_security_code")
public class RegistrationSecurityCode implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private UUID code;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Column(name = "email")
    private String email;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

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

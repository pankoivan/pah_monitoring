package org.pah_monitoring.main.entities.security_codes;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.enums.ExpirationDate;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "page_access_security_code")
public class PageAccessSecurityCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private UUID code;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @Transient
    private transient ExpirationDate expirationDateEnum;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof PageAccessSecurityCode other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

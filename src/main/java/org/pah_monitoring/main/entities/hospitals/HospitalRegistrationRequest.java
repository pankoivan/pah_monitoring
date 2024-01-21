package org.pah_monitoring.main.entities.hospitals;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.common.interfaces.BaseEntity;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = "hospital")
@Builder
@JsonIgnoreProperties("hospital")
@Entity
@Table(name = "hospital_registration_request")
public class HospitalRegistrationRequest implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "post")
    private String post;

    @Column(name = "passport")
    private String passport;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private LocalDateTime date;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof HospitalRegistrationRequest other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

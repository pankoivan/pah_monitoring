package org.pah_monitoring.main.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString // todo
@Entity
@Table(name = "hospital_employee_information")
public class HospitalEmployeeInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "experience")
    private Integer experience;

    @OneToOne
    @JoinColumn(name = "user_information_id")
    private UserInformation userInformation;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof HospitalEmployeeInformation other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

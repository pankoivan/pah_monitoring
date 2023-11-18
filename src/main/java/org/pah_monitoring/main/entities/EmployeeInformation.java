package org.pah_monitoring.main.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "experience"})
@Entity
@Table(name = "hospital_employee_information")
public class EmployeeInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "experience")
    private Integer experience;

    @OneToOne
    @JoinColumn(name = "user_information_id")
    private UserInformation userInformation;

    @OneToMany(mappedBy = "employee")
    private List<Vacation> vacations;

    @OneToMany(mappedBy = "employee")
    private List<SickLeave> sickLeaves;

    @OneToOne(mappedBy = "employee")
    private Dismissal dismissal;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof EmployeeInformation other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

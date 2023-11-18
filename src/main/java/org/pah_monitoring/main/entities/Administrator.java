package org.pah_monitoring.main.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "hospitalId"})
@Entity
@Table(name = "administrator")
public class Administrator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "hospital_id")
    private String hospitalId;

    @OneToOne
    @JoinColumn(name = "user_security_information_id")
    private UserSecurityInformation userSecurityInformation;

    @OneToOne
    @JoinColumn(name = "hospital_employee_information_id")
    private EmployeeInformation employeeInformation;

    @OneToMany(mappedBy = "administrator")
    private List<Vacation> assignedVacations;

    @OneToMany(mappedBy = "administrator")
    private List<SickLeave> assignedSickLeaves;

    @OneToMany(mappedBy = "administrator")
    private List<Dismissal> assignedDismissals;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Administrator other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

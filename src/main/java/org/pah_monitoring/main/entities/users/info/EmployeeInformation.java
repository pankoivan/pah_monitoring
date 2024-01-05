package org.pah_monitoring.main.entities.users.info;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.other.Hospital;
import org.pah_monitoring.main.entities.users.inactivity.Dismissal;
import org.pah_monitoring.main.entities.users.inactivity.SickLeave;
import org.pah_monitoring.main.entities.users.inactivity.Vacation;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = {"id", "experience"})
@Builder
@Entity
@Table(name = "hospital_employee_information")
public class EmployeeInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "experience")
    private Integer experience;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

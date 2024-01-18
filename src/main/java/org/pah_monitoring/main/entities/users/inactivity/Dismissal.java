package org.pah_monitoring.main.entities.users.inactivity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.users.users.Administrator;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"employee", "author"})
@Builder
@JsonIgnoreProperties({"employee", "author"})
@Entity
@Table(name = "dismissal")
public class Dismissal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "comment")
    private String comment;

    @OneToOne
    @JoinColumn(name = "hospital_employee_information_id")
    private EmployeeInformation employee;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Administrator author;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Dismissal other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

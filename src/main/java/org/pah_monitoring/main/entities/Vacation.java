package org.pah_monitoring.main.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"employee", "author"})
@Entity
@Table(name = "vacation")
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "hospital_employee_information_id")
    private EmployeeInformation employee;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Administrator author;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof Vacation other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

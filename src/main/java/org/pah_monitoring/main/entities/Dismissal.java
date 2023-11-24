package org.pah_monitoring.main.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"employee", "author"})
@Entity
@Table(name = "hospital_employee_dismissal")
public class Dismissal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

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

package org.pah_monitoring.main.entities.main.users.inactivity;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.main.users.inactivity.common.Inactivity;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.main.users.users.Administrator;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"employee", "author"})
@Builder
@Entity
@Table(name = "dismissal")
public class Dismissal extends Inactivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "hospital_employee_information_id")
    private EmployeeInformation employee;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Administrator author;

    public String getFormattedDate() {
        return DateTimeFormatConstants.DAY_MONTH_YEAR.format(date);
    }

    @Override
    public String getInactivityMessage() {
        return "Уволен %s".formatted(getFormattedDate());
    }

    @Override
    public String getAuthorMessagePart() {
        return "Кем уволен:";
    }

    @Override
    public String getAuthorFullName() {
        return author.getUserInformation().getFullName();
    }

    @Override
    public Integer getAuthorUserInformationId() {
        return author.getUserInformation().getId();
    }

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

package org.pah_monitoring.main.entities.main.users.inactivity;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.main.users.inactivity.common.interfaces.Inactivity;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.main.users.users.Administrator;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@Entity
@Table(name = "sick_leave")
public class SickLeave implements Inactivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "hospital_employee_information_id")
    private EmployeeInformation employee;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Administrator author;

    public String getFormattedStartDate() {
        return DateTimeFormatConstants.DAY_MONTH_YEAR.format(startDate);
    }

    public String getFormattedEndDate() {
        return DateTimeFormatConstants.DAY_MONTH_YEAR.format(endDate);
    }

    @Override
    public String getShortInactivityMessage() {
        return "На больничном";
    }

    @Override
    public String getInactivityMessage() {
        return "На больничном с %s по %s".formatted(getFormattedStartDate(), getFormattedEndDate());
    }

    @Override
    public String getAuthorMessagePart() {
        return "Кем назначен больничный:";
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
                || ((o instanceof SickLeave other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

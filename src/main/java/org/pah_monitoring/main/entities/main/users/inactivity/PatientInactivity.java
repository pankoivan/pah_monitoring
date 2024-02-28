package org.pah_monitoring.main.entities.main.users.inactivity;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.main.users.inactivity.common.interfaces.Inactivity;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.entities.main.users.users.Patient;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@Entity
@Table(name = "patient_inactivity")
public class PatientInactivity implements Inactivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "date")
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Doctor author;

    public String getFormattedDate() {
        return DateTimeFormatConstants.DAY_MONTH_YEAR.format(date);
    }

    @Override
    public String getShortInactivityMessage() {
        return "Переведён в неактивное состояние";
    }

    @Override
    public String getInactivityMessage() {
        return "Переведён в неактивное состояние %s".formatted(getFormattedDate());
    }

    @Override
    public String getAuthorMessagePart() {
        return "Кем переведён:";
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
                || ((o instanceof PatientInactivity other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

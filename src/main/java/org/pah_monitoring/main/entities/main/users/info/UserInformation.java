package org.pah_monitoring.main.entities.main.users.info;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.main.enums.Gender;
import org.pah_monitoring.main.entities.main.users.messages.UserMessage;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(of = "id")
@Builder
@Entity
@Table(name = "user_information")
public class UserInformation implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @OneToMany(mappedBy = "author")
    private List<UserMessage> sentMessages;

    @OneToMany(mappedBy = "recipient")
    private List<UserMessage> receivedMessages;

    public String getFullName() {
        return patronymic != null
                ? "%s %s %s".formatted(lastname, name, patronymic)
                : "%s %s".formatted(lastname, name);
    }

    public String getFormattedBirthdate() {
        return birthdate != null
                ? DateTimeFormatConstants.DAY_MONTH_YEAR.format(birthdate)
                : null;
    }

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof UserInformation other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

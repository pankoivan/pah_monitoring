package org.pah_monitoring.main.entities.users.info;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.enums.Gender;
import org.pah_monitoring.main.entities.users.messages.UserMessage;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"sentMessages", "receivedMessages"})
@Builder
@JsonIgnoreProperties({"sentMessages", "receivedMessages"})
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

    @JsonIgnore
    public String getFullName() {
        return !patronymic.isEmpty()
                ? "%s %s %s".formatted(lastname, name, patronymic)
                : "%s %s".formatted(lastname, name);
    }

    @JsonIgnore
    public String getFormattedBirthdate() {
        return DateTimeFormatConstants.DAY_MONTH_YEAR.format(birthdate);
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

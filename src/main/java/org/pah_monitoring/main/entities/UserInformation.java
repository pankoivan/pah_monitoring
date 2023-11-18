package org.pah_monitoring.main.entities;

import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.main.entities.enums.Gender;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"sentMessages", "receivedMessages"})
@Entity
@Table(name = "user_information")
public class UserInformation {

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

    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birthdate")
    private LocalDate birthdate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(mappedBy = "author")
    private List<UserMessage> sentMessages;

    @OneToMany(mappedBy = "recipient")
    private List<UserMessage> receivedMessages;

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

package org.pah_monitoring.main.entities;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString // todo
@Entity
@Table(name = "patient")
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message_text")
    private String text;

    @Column(name = "receipt_date")
    private String date;

    @ManyToOne
    @JoinColumn(name = "user_information_id")
    private UserInformation recipient;

    @ManyToOne
    @JoinColumn(name = "user_information_id")
    private UserInformation author;

    @Override
    public boolean equals(Object o) {
        return (this == o)
                || ((o instanceof UserMessage other))
                && (id != null)
                && (id.equals(other.id));
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}

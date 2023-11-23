package org.pah_monitoring.main.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"recipient", "author"})
@Entity
@Table(name = "user_message")
public class UserMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message_text")
    private String text;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserInformation recipient;

    @ManyToOne
    @JoinColumn(name = "author_id")
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

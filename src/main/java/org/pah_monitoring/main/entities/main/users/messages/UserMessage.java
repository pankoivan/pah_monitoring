package org.pah_monitoring.main.entities.main.users.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"recipient", "author"})
@Builder
@JsonIgnoreProperties({"recipient", "author"})
@Entity
@Table(name = "user_message")
public class UserMessage implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "message_text")
    private String text;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "editing_date")
    private LocalDateTime editingDate;

    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserInformation recipient;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserInformation author;

    public String getFormattedDate() {
        return DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(date);
    }

    public String getFormattedEditingDate() {
        return editingDate != null
                ? DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(editingDate)
                : null;
    }

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

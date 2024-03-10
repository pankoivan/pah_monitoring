package org.pah_monitoring.main.entities.main.users.messages;

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
@ToString(of = "id")
@Builder
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

    public String getShortFormattedDate() {
        return DateTimeFormatConstants.HOUR_MINUTE__READABLE.format(date);
    }

    public String getLongFormattedDate() {
        return DateTimeFormatConstants.DAY_MONTH_YEAR_COMMA_HOUR_MINUTE__READABLE.format(date);
    }

    public String getShortFormattedEditingDate() {
        return editingDate != null
                ? DateTimeFormatConstants.HOUR_MINUTE__READABLE.format(editingDate)
                : null;
    }

    public String getLongFormattedEditingDate() {
        return editingDate != null
                ? DateTimeFormatConstants.DAY_MONTH_YEAR_COMMA_HOUR_MINUTE__READABLE.format(editingDate)
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

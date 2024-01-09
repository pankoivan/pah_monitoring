package org.pah_monitoring.main.controllers.mvc;

import lombok.*;
import org.pah_monitoring.main.entities.enums.ExpirationDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class TestEntity {

    private String name;

    private String lastname;

    private String patronymic;

    private ExpirationDate expirationDate;

}

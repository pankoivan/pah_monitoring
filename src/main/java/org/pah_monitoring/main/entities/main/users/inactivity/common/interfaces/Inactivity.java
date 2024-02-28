package org.pah_monitoring.main.entities.main.users.inactivity.common.interfaces;

import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;

public interface Inactivity extends BaseEntity {

    String getShortInactivityMessage();

    String getInactivityMessage();

    String getAuthorMessagePart();

    String getAuthorFullName();

    Integer getAuthorUserInformationId();

    String getComment();

}

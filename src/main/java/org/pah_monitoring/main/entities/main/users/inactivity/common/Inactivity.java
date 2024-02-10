package org.pah_monitoring.main.entities.main.users.inactivity.common;

import org.pah_monitoring.main.entities.main.common.interfaces.BaseEntity;

public abstract class Inactivity implements BaseEntity {

    public abstract String getShortInactivityMessage();

    public abstract String getInactivityMessage();

    public abstract String getAuthorMessagePart();

    public abstract String getAuthorFullName();

    public abstract Integer getAuthorUserInformationId();

    public abstract String getComment();

}

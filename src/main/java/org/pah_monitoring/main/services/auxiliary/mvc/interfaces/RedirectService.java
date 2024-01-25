package org.pah_monitoring.main.services.auxiliary.mvc.interfaces;

public interface RedirectService {

    boolean checkMainRedirect();

    String mainRedirect();

    boolean checkNotAnonymousUserRedirect();

    String notAnonymousUserRedirect();

}

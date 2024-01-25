package org.pah_monitoring.main.services.auxiliary.redirect.interfaces;

public interface RedirectService {

    boolean checkMainRedirect();

    String mainRedirect();

    boolean checkNotAnonymousUserRedirect();

    String notAnonymousUserRedirect();

}

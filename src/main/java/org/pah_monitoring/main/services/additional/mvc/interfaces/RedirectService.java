package org.pah_monitoring.main.services.additional.mvc.interfaces;

public interface RedirectService {

    boolean checkMainRedirect();

    String mainRedirect();

    boolean checkNotAnonymousUserRedirect();

    String notAnonymousUserRedirect();

    boolean checkPatientAnamnesisRedirect();

    String patientAnamnesisRedirect();

}

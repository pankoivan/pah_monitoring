package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;
import org.pah_monitoring.auxiliary.exceptions.AuthenticationUtilsException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

@UtilityClass
public final class AuthenticationUtils {

    public static <T> T extractCurrentUser(Authentication authentication, Class<T> concreteUserClass)
            throws AuthenticationUtilsException {

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new AuthenticationUtilsException("Security user (i.e. principal) should not be null or anonymous");
        }

        try {
            return cast(authentication.getPrincipal(), concreteUserClass);
        } catch (ClassCastException e) {
            throw new AuthenticationUtilsException("Security user (i.e. principal) is not an object of requested class \"%s\""
                    .formatted(concreteUserClass.getName()), e);
        }
    }

    public static <T> T extractCurrentUserOrNull(Authentication authentication, Class<T> concreteUserClass)
            throws AuthenticationUtilsException {

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }

        try {
            return cast(authentication.getPrincipal(), concreteUserClass);
        } catch (ClassCastException e) {
            throw new AuthenticationUtilsException("Security user (i.e. principal) is not an object of requested class \"%s\""
                    .formatted(concreteUserClass.getName()), e);
        }
    }

    public <T> boolean checkConcreteUserClass(Authentication authentication, Class<T> concreteUserClass) {
        return authentication.getPrincipal().getClass().equals(concreteUserClass);
    }

    private static <T> T cast(Object from, Class<T> to) {
        return to.cast(from);
    }

}

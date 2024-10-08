package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;
import org.pah_monitoring.main.exceptions.utils.UrlUtilsException;

@UtilityClass
public final class UrlUtils {

    public static String buildUrlWithGetParameters(String baseUrl, Object... parameters) throws UrlUtilsException {

        if (baseUrl == null || parameters.length == 0 || parameters.length % 2 != 0) {
            throw new UrlUtilsException("Некорректный набор параметров");
        }

        StringBuilder sb = new StringBuilder(baseUrl);
        sb.append("?");
        for (int i = 0; i < parameters.length - 1; ++i) {
            sb
                    .append(parameters[i])
                    .append("=")
                    .append(parameters[i + 1])
                    .append("&");
        }
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();

    }

    public static String getLastUrlPart(String url) throws UrlUtilsException {

        if (url == null || !url.contains("/")) {
            throw new UrlUtilsException("Некорректный формат URL: \"%s\"".formatted(url));
        }

        String[] parts = url.split("/");
        return parts[parts.length - 1];

    }

}

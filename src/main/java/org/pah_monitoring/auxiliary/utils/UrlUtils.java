package org.pah_monitoring.auxiliary.utils;

import lombok.experimental.UtilityClass;
import org.pah_monitoring.main.exceptions.utils.UrlUtilsException;

import java.util.Map;
import java.util.stream.Collectors;

@UtilityClass
public final class UrlUtils {

    public static String buildUrlWithGetParameters(String baseUrl, Object ... parameters) throws UrlUtilsException {

        if (parameters.length == 0 || parameters.length % 2 != 0) {
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

    public static Map<String, String> normalizeGetParametersMap(Map<String, String[]> parameters) {

        return parameters.entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), entry.getValue()[0]))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

}

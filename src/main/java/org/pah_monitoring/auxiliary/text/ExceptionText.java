package org.pah_monitoring.auxiliary.text;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ExceptionText {

    public static final String URL_UTILS_EXCEPTION = "Некорректный набор параметров.";

    public static final String UUID_UTILS_EXCEPTION = "Некорректный формат UUID: \"%s\".";

    public static final String MESSAGE_CONVERSION = """
            Произошла ошибка при преобразовании между JSON и Java-объектом. Это может быть связано с несовпадением типов\
             полей или с пустотой участвующих в преобразовании данных.
            """;

    public static final String MAX_FILE_SIZE_EXCEEDED = "Превышен максимальный размер файла.";

    public static final String HAS_NO_DOCTOR = """
            Вы не можете отправлять результаты наблюдений, так как на данный момент за вами не закреплён ни\
             один врач. Ожидайте, пока администраторы назначат вам какого-нибудь врача, или обратитесь к ним\
             посредством личных сообщений в случае долгого ожидания.
            """;

    public static final String HAS_NO_ANAMNESIS = """
            Вы не можете отправлять результаты наблюдений, так как на данный момент у вас ещё не отправлен анамнез.\
             Прежде чем отправлять результаты наблюдений, вам необходимо сначала отправить ваш анамнез.
            """;

}

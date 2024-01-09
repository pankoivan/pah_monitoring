package org.pah_monitoring.auxiliary.text;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class HttpErrorText {

    public static final String titleXxx = "Ошибка %s";

    public static final String textNotHandledXxx = """
            Для ошибки с кодом %s на сервере не предусмотрен поясняющий текст. Если вы хотите больше узнать об этой ошибке,\
             можете поискать информацию в Интернете.
            """;

    public static final String title400 = "Ошибка 400";

    public static final String text400 = """
            Некорректный URL-адрес. Скорее всего, вы вручную набрали его в адресной строке, поэтому возникла такая ошибка.\
             Для осуществления всех действий на сайте используйте только предоставляемые приложением ссылки и кнопки.
            """;

    public static final String title401 = "Ошибка 401";

    public static final String text401 = """
            Вы не аутентифицированы. Для того, чтобы получить страницу по данному URL-адресу, войдите в систему.\
             Если у вас нет аккаунта, создайте его, зарегистрировавшись в приложении.
            """;

    public static final String title403 = "Ошибка 403";

    public static final String text403 = """
            У вас недостаточно прав. Это значит, что роль вашего аккаунта в приложении не подразумевает обращения по данному\
             URL-адресу.
            """;

    public static final String title404 = "Ошибка 404";

    public static final String text404 = """
            Страница не найдена. По данному URL-адресу отсутствуют какие-либо ресурсы. Скорее всего, вы вручную набрали\
             этот URL в адресной строке. Для навигации по сайту используйте специально предназначенные для этого ссылки и кнопки.
            """;

    public static final String title405 = "Ошибка 405";

    public static final String text405 = """
            Неподходящий HTTP-метод. Воспользуйтесь другим HTTP-методом для получения страницы по данному URL-адресу.\
             Текущий метод не предназначен для обращения по этому адресу.
            """;

    public static final String titleUnexpectedServerError = "Неожиданная ошибка сервера";

    public static final String textUnexpectedServerError = """
            На сервере возникла неожиданная ошибка. Мы уже работаем над устранением её возможной причины. Это может занять\
             некоторое время.
            """;

}

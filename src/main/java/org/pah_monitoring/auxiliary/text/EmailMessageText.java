package org.pah_monitoring.auxiliary.text;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class EmailMessageText {

    public static final String registrationCodeTitle = "Код регистрации в приложении \"%s\"";

    public static final String registrationCodeText = """
            Здравствуйте!
             
            Для регистрации в приложении "%s" от медицинского учреждения "%s" на роль "%s" вам был выдан код: %s.\
             Он предназначен только для вашей регистрации: никому его не сообщайте!
            
            Срок действия кода истечёт %s. Успейте зарегистрироваться до этого времени!
            """;

}

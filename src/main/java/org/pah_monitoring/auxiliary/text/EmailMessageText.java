package org.pah_monitoring.auxiliary.text;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class EmailMessageText {

    public static final String registrationCodeTitle = "Код регистрации";

    public static final String registrationCodeText = """
            <b>Здравствуйте!</b>
            
            <br/><br/>
            
            Для регистрации в приложении «%s» от медицинского учреждения «%s» на роль <b>%s</b> вам был выдан код:
            
            <br/><br/>
             
            <b style="font-size: 24px">%s</b>
            
            <br/><br/>
            
            Он предназначен только для вашей регистрации: никому его не сообщайте!
             
            <br/><br/>
            
            Срок действия выданного кода истечёт <b>%s</b>. Успейте зарегистрироваться до этого времени!
            """;

    public static final String patientDoctorAssigningTitle = "Новый лечащий врач";

    public static final String patientDoctorAssigningText = """
            <b>Здравствуйте!</b>
            
            <br/><br/>
            
            Уведомляем вас о том, что теперь вашим лечащим врачом является <a href="http://localhost:8080/doctors/%s">%s</a>.
            
            <br/><br/>
            
            С этого момента он будет заниматься просмотром и анализом отправляемых вами показателей и выдавать дальнейшие
             указания. Для общения с ним вы можете использовать <a href="http://localhost:8080/messages/%s">личные сообщения</a>.
            """;

}

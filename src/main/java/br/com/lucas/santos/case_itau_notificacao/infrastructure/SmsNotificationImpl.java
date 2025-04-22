package br.com.lucas.santos.case_itau_notificacao.infrastructure;

import br.com.lucas.santos.case_itau_notificacao.entities.interfaces.SendingNotificationInterface;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class SmsNotificationImpl implements SendingNotificationInterface {

    @Value("${ACCOUNT_SID}")
    private String ACCOUNT_SID;
    @Value("${AUTH_TOKEN}")
    private String AUTH_TOKEN;
    @Value("${PHONE_NUMBER_TWILLIO}")
    private String PHONE_NUMBER_TWILLIO;
    @Value("${MY_PHONE_NUMBER}")
    private String PERSONAL_PHONE_NUMBER;
    private Message messageSms;

    @Override
    public void sendNotification(String message) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        messageSms = Message.creator(
                        new com.twilio.type.PhoneNumber(PERSONAL_PHONE_NUMBER),
                        new com.twilio.type.PhoneNumber(PHONE_NUMBER_TWILLIO),
                        message)
                .create();
        log.info("SMS enviado para: {}", messageSms.getTo());
    }
}

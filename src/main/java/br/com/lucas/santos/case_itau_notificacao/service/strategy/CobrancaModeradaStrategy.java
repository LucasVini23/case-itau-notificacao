package br.com.lucas.santos.case_itau_notificacao.service.strategy;

import br.com.lucas.santos.case_itau_notificacao.entities.NotificationEntity;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.EmailNotificationImpl;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.SmsNotificationImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Period;

@Component
@RequiredArgsConstructor
@Slf4j
public class CobrancaModeradaStrategy implements ChargeStrategy {

    private final SmsNotificationImpl smsNotification;
    private final EmailNotificationImpl emailNotification;

    @Override
    public void charge(Period period, NotificationEntity notification) {
        if (period.getDays() > 5 && period.getDays() <= 30){
            String message = "cobrança moderada";
            emailNotification.sendNotification(message);
            smsNotification.sendNotification(message);
//            sendSms(message);
            notification.setMessage(message);
            log.info("cobrança feita com sucesso: {}", message);
        }
    }
}

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
public class CobrancaPesadaStrategy implements ChargeStrategy {

    private final SmsNotificationImpl smsNotification;
    private final EmailNotificationImpl emailNotification;

    @Override
    public void charge(Period period, NotificationEntity notification) {
        if (period.getMonths() >= 1){
            String message = "cobrança pesada";
            emailNotification.sendNotification(message);
            smsNotification.sendNotification(message);
//            sendSms(message);
            notification.setMessage(message);
            log.info("cobrança feita com sucesso: {}", message);
        }
    }
}

package br.com.lucas.santos.case_itau_notificacao.service.strategy;

import br.com.lucas.santos.case_itau_notificacao.infrastructure.EmailNotificationImpl;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.SmsNotificationImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Period;

@Component
@RequiredArgsConstructor
@Slf4j
public class CobrancaLeveStrategy implements ChargeStrategy {

    private final SmsNotificationImpl smsNotification;
    private final EmailNotificationImpl emailNotification;

    @Override
    public void charge(Period period) {
        if(period.getDays() > 1 && period.getDays() < 5){
            String message = "cobrança leve";
            emailNotification.sendNotification(message);
            smsNotification.sendNotification(message);
//            sendSms(message);
            log.info("cobrança feita com sucesso: {}", message);
        }
    }
}

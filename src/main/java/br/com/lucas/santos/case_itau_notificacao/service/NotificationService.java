package br.com.lucas.santos.case_itau_notificacao.service;

import br.com.lucas.santos.case_itau_notificacao.adapters.repository.NotificationRepository;
import br.com.lucas.santos.case_itau_notificacao.entities.NotificationEntity;
import br.com.lucas.santos.case_itau_notificacao.entities.UserBatch;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.EmailNotificationImpl;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.MapNotification;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.SmsNotificationImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final MapNotification mapper;
    private final NotificationRepository repository;
    private final SmsNotificationImpl smsNotification;
    private final EmailNotificationImpl emailNotification;

    @SqsListener("fila-appNotification")
    private void listenerQueueNotification(String message) throws JsonProcessingException {
        ObjectMapper mapper1 = new ObjectMapper();
        mapper1.findAndRegisterModules();
        UserBatch user = mapper1.readValue(message, UserBatch.class);
        notification(user);
    }

    private void notification(UserBatch user) {
        NotificationEntity notification = mapper.toNotification(user);
        String message = "";
        Period period = Period.between(user.dueDate(), LocalDate.now());

//        notification.setPath(List.of(ShippingEnum.SMS.getValue(), ShippingEnum.EMAIL.getValue()));
        if(period.getDays() > 1 && period.getDays() < 5){
            message = "cobrança leve";
            emailNotification.sendNotification(message);
            smsNotification.sendNotification(message);
//            sendSms(message);
            log.info("cobrança feita com sucesso: {}", message);
        }
        else if (period.getDays() > 5 && period.getDays() <= 30){
            message = "cobrança moderada";
            emailNotification.sendNotification(message);
            smsNotification.sendNotification(message);
//            sendSms(message);
            log.info("cobrança feita com sucesso: {}", message);
        }
        else if (period.getDays() > 30 && period.getDays() <= 90){
            message = "cobrança pesada";
            emailNotification.sendNotification(message);
            smsNotification.sendNotification(message);
//            sendSms(message);
            log.info("cobrança feita com sucesso: {}", message);
        }
        notification.setShippingDate(LocalDate.now());
        notification.setMessage(message);

        repository.save(notification);
        log.info("notificação salva: {}", notification);
    }

}

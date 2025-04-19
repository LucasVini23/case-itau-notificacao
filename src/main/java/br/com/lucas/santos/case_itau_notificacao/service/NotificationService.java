package br.com.lucas.santos.case_itau_notificacao.service;

import br.com.lucas.santos.case_itau_notificacao.entity.SentNotification;
import br.com.lucas.santos.case_itau_notificacao.entity.UserBatch;
import br.com.lucas.santos.case_itau_notificacao.feature.MapNotification;
import br.com.lucas.santos.case_itau_notificacao.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender emailSender;

    @Value("${ACCOUNT_SID}")
    private String ACCOUNT_SID;
    @Value("${AUTH_TOKEN}")
    private String AUTH_TOKEN;
    @Value("${PHONE_NUMBER_TWILLIO}")
    private String PHONE_NUMBER_TWILLIO;
    @Value("${MY_PHONE_NUMBER}")
    private String PERSONAL_PHONE_NUMBER;

    private final MapNotification mapper;
    private final NotificationRepository repository;

    @SqsListener("fila-appNotification")
    private void listenerQueueNotification(String message) throws JsonProcessingException {
        ObjectMapper mapper1 = new ObjectMapper();
        mapper1.findAndRegisterModules();
        UserBatch user = mapper1.readValue(message, UserBatch.class);
        notification(user);
    }

    private void notification(UserBatch user) throws JsonProcessingException {
        SentNotification notification = mapper.toNotification(user);
        String message = "Teste de envio de mensagem";
//        notification.setPath(List.of(ShippingEnum.SMS.getValue(), ShippingEnum.EMAIL.getValue()));
        notification.setShippingDate(LocalDate.now());
        notification.setMessage(message);

        sendEmail(message);

//        repository.save(notification);
        log.info("notificação salva: {}", notification);
    }

    private void sendEmail(String message) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("");
        email.setTo("");
        email.setSubject("Cobrança da Divida");
        email.setText(message);
        emailSender.send(email);
    }

    private void sendSms(String message){
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message sms = Message.creator(
                        new com.twilio.type.PhoneNumber(PERSONAL_PHONE_NUMBER),
                        new com.twilio.type.PhoneNumber(PHONE_NUMBER_TWILLIO),
                        message)
                .create();

        System.out.println(sms.getSid());
    }

}

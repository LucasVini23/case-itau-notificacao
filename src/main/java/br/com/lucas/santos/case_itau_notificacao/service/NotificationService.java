package br.com.lucas.santos.case_itau_notificacao.service;

import br.com.lucas.santos.case_itau_notificacao.adapters.repository.NotificationRepository;
import br.com.lucas.santos.case_itau_notificacao.entities.NotificationEntity;
import br.com.lucas.santos.case_itau_notificacao.entities.UserBatch;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.EmailNotificationImpl;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.MapNotification;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.SmsNotificationImpl;
import br.com.lucas.santos.case_itau_notificacao.service.strategy.ChargeStrategy;
import br.com.lucas.santos.case_itau_notificacao.service.strategy.CobrancaLeveStrategy;
import br.com.lucas.santos.case_itau_notificacao.service.strategy.CobrancaModeradaStrategy;
import br.com.lucas.santos.case_itau_notificacao.service.strategy.CobrancaPesadaStrategy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final MapNotification mapNotification;
    private final NotificationRepository repository;
    private final SmsNotificationImpl smsNotification;
    private final EmailNotificationImpl emailNotification;
    private final ObjectMapper mapper;

    @SqsListener("fila-appNotification")
    private void listenerQueueNotification(String message) throws JsonProcessingException {
        mapper.findAndRegisterModules();
        UserBatch user = mapper.readValue(message, UserBatch.class);
        notification(user);
    }

    private void notification(UserBatch user) {
        NotificationEntity notification = mapNotification.toNotification(user);
        String message = "";
        Period period = Period.between(user.dueDate(), LocalDate.now());

        List<ChargeStrategy> strategies = List.of(new CobrancaLeveStrategy(smsNotification, emailNotification),
                new CobrancaModeradaStrategy(smsNotification, emailNotification),
                new CobrancaPesadaStrategy(smsNotification, emailNotification));

        for (ChargeStrategy strategy : strategies) {
            strategy.charge(period);
        }

//        notification.setPath(List.of(ShippingEnum.SMS.getValue(), ShippingEnum.EMAIL.getValue()));
        notification.setShippingDate(LocalDate.now());
        notification.setMessage(message);

        repository.save(notification);
        log.info("notificação salva: {}", notification);
    }

}

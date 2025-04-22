package br.com.lucas.santos.case_itau_notificacao.service;

import br.com.lucas.santos.case_itau_notificacao.adapters.repository.NotificationRepository;
import br.com.lucas.santos.case_itau_notificacao.entities.NotificationEntity;
import br.com.lucas.santos.case_itau_notificacao.entities.UserBatch;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.EmailNotificationImpl;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.MapNotification;
import br.com.lucas.santos.case_itau_notificacao.infrastructure.SmsNotificationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private MapNotification mapNotification;

    @Mock
    private NotificationRepository repository;

    @Mock
    private SmsNotificationImpl smsNotification;

    @Mock
    private EmailNotificationImpl emailNotification;

    @Test
    void testTreatmentNotificationLeve() {
        UserBatch user = userBuilderLeve();
        NotificationEntity notification = notificationBuilder();
        String message = "cobrança leve";

        when(mapNotification.toNotification(user)).thenReturn(notification);
        when(repository.save(notification)).thenReturn(notification);
        doNothing().when(emailNotification).sendNotification(message);
        doNothing().when(smsNotification).sendNotification(message);

        notificationService.notification(user);

        assertEquals("cobrança leve", notification.getMessage());

    }

    @Test
    void testTreatmentNotificationModerada() {
        UserBatch user = userBuilderModerada();
        NotificationEntity notification = notificationBuilder();
        String message = "cobrança moderada";

        when(mapNotification.toNotification(user)).thenReturn(notification);
        when(repository.save(notification)).thenReturn(notification);
        doNothing().when(emailNotification).sendNotification(message);
        doNothing().when(smsNotification).sendNotification(message);

        notificationService.notification(user);

        assertEquals("cobrança moderada", notification.getMessage());

    }

    @Test
    void testTreatmentNotificationPesada() {
        UserBatch user = userBuilderPesada();
        NotificationEntity notification = notificationBuilder();
        String message = "cobrança pesada";

        when(mapNotification.toNotification(user)).thenReturn(notification);
        when(repository.save(notification)).thenReturn(notification);
        doNothing().when(emailNotification).sendNotification(message);
        doNothing().when(smsNotification).sendNotification(message);

        notificationService.notification(user);

        assertEquals("cobrança pesada", notification.getMessage());

    }

    private UserBatch userBuilderLeve(){
        return new UserBatch("Lucas",
                "12345678910",
                "Lucas@gmail.com",
                List.of("11111111111", "999999999"),
                List.of("Rua", "Avenida"),
                null,
                LocalDate.now().minusDays(1),
                LocalDate.now());
    }

    private UserBatch userBuilderModerada(){
        return new UserBatch("Lucas",
                "12345678910",
                "Lucas@gmail.com",
                List.of("11111111111", "999999999"),
                List.of("Rua", "Avenida"),
                null,
                LocalDate.now().minusDays(20),
                LocalDate.now());
    }

    private UserBatch userBuilderPesada(){
        return new UserBatch("Lucas",
                "12345678910",
                "Lucas@gmail.com",
                List.of("11111111111", "999999999"),
                List.of("Rua", "Avenida"),
                null,
                LocalDate.now().minusDays(31),
                LocalDate.now());
    }

    private NotificationEntity notificationBuilder() {
        return NotificationEntity.builder()
                .name("Lucas")
                .email("Lucas@gmail.com")
                .phones(List.of("11111111111", "999999999")).build();
    }
}
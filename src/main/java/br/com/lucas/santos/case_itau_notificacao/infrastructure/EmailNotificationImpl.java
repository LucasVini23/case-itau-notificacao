package br.com.lucas.santos.case_itau_notificacao.infrastructure;

import br.com.lucas.santos.case_itau_notificacao.entities.interfaces.SendingNotificationInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailNotificationImpl implements SendingNotificationInterface {

    private final JavaMailSender emailSender;
    private final SimpleMailMessage email;

    @Override
    public void sendNotification(String message) {
        email.setFrom("lucasvinipro23@gmail.com ");
        email.setTo("lucasvinipro23@gmail.com");
        email.setSubject("Cobrança da Divida");
        email.setText(message);
        emailSender.send(email);
        log.info("Email enviado para: {}", email.getTo());
    }
}

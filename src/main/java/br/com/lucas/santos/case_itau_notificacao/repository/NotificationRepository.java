package br.com.lucas.santos.case_itau_notificacao.repository;

import br.com.lucas.santos.case_itau_notificacao.entity.SentNotification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<SentNotification, String> {
}

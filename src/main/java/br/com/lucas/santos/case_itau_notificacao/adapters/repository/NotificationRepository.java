package br.com.lucas.santos.case_itau_notificacao.adapters.repository;

import br.com.lucas.santos.case_itau_notificacao.entities.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {
}

package br.com.lucas.santos.case_itau_notificacao.service.strategy;

import br.com.lucas.santos.case_itau_notificacao.entities.NotificationEntity;

import java.time.Period;

public interface ChargeStrategy {

    void charge(Period period, NotificationEntity notification);
}

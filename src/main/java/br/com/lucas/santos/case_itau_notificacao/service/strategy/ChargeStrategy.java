package br.com.lucas.santos.case_itau_notificacao.service.strategy;

import java.time.Period;

public interface ChargeStrategy {

    void charge(Period period);
}

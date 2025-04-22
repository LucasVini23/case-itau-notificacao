package br.com.lucas.santos.case_itau_notificacao.entities;

import java.time.LocalDate;
import java.util.List;

public record UserBatch(String name, String document, String email, List<String> phones, List<String> addresses, String status, LocalDate dueDate, LocalDate notification) {

}


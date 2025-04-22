package br.com.lucas.santos.case_itau_notificacao.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "notifications")
@Data
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private List<String> phones;
    private String message;
//    private List<String> path; Guarda os lugares para onde as notificações foram enviadas
    private LocalDate shippingDate;
}

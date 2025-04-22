package br.com.lucas.santos.case_itau_notificacao.infrastructure;


import br.com.lucas.santos.case_itau_notificacao.entities.NotificationEntity;
import br.com.lucas.santos.case_itau_notificacao.entities.UserBatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapNotification {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phones", target = "phones")
    NotificationEntity toNotification(UserBatch user);
}

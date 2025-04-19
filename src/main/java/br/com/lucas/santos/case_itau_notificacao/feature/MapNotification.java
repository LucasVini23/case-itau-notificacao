package br.com.lucas.santos.case_itau_notificacao.feature;


import br.com.lucas.santos.case_itau_notificacao.entity.SentNotification;
import br.com.lucas.santos.case_itau_notificacao.entity.UserBatch;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapNotification {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "phones", target = "phones")
    SentNotification toNotification(UserBatch user);
}

package br.com.lucas.santos.case_itau_notificacao.entities.enums;

public enum ShippingEnum {

    SMS("sms"),
    EMAIL("email");

    private String value;

    ShippingEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

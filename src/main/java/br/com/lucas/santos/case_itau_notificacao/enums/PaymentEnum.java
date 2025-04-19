package br.com.lucas.santos.case_itau_notificacao.enums;

public enum PaymentEnum {

    PAID("PAID"),
    OWING("OWING");

    private String value;

    PaymentEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

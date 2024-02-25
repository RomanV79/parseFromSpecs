package ru.vlasov.entity;

public enum Type {
    EMPTY(""),
    STRING("string"),
    INTEGER("integer"),
    LONG("integer");

    private final String value;

    Type(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

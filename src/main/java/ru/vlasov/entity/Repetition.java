package ru.vlasov.entity;

public enum Repetition {
    ZERO("0"),
    ZERO_TO_ONE("0..1"),
    ONE("1"),
    ZERO_TO_UNBOUNDED("0..неограниченно"),
    ONE_TO_UNBOUNDED("1..неограниченно");

    private final String value;

    Repetition(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

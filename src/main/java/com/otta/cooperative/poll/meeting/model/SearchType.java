package com.otta.cooperative.poll.meeting.model;

import java.util.Arrays;

public enum SearchType {
    ALL("ALL"),
    OPEN("OPEN"),
    UNKNOWN("UNKNOWN");

    private final String label;

    private SearchType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Método para retornar o elemento de {@link SearchType} com o
     * {@link SearchType#label} com o mesmo valor do paramêtro, utilizando
     * ignoreCase.</br>
     * Caso não encontre um valor correspondente, retorna
     * {@link SearchType#UNKNOWN}.
     */
    public static SearchType getEnum(String value) {
        return Arrays.stream(values()).filter(type -> type.getLabel().equalsIgnoreCase(value)).findFirst()
                .orElse(UNKNOWN);
    }
}

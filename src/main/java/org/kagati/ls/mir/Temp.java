package org.kagati.ls.mir;

public record Temp(int value) {
    @Override
    public final String toString() {
        return "%" + value;
    }
}
package org.kagati.ls.mir.node;

public record Temp(int value) {
    @Override
    public final String toString() {
        return "%" + value;
    }
}
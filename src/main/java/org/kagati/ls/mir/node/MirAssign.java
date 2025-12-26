package org.kagati.ls.mir.node;

import org.kagati.ls.mir.Temp;

public record MirAssign(String target, Temp value) implements MirInstruction {
    @Override
    public String toString() {
        return String.format("%s = %s\n", target, value.toString());
    }
}
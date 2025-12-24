package org.kagati.ls.mir.node;

public record MirAssign(String target, String source) implements MirInstruction {
    @Override
    public String toString() {
        return String.format("%s = %s\n", target, source);
    }
}
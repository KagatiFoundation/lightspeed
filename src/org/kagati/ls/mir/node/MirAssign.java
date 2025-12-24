package org.kagati.ls.mir.node;

public record MirAssign(String target, String source) implements MirInstruction {
    @Override
    public void dump() {
        System.out.printf("%s = %s\n", target, source);
    }
}
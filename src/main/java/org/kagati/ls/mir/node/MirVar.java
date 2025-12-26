package org.kagati.ls.mir.node;

public record MirVar(String target) implements MirExpr {
    @Override
    public final String toString() {
        return String.format("_%s", target);
    }
}
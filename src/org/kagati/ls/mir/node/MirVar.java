package org.kagati.ls.mir.node;

public record MirVar(String target) implements MirExpr {
    @Override
    public final String toString() {
        return target;
    }
}
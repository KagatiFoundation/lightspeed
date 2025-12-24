package org.kagati.ls.hir.node;

public record HirVar(String name) implements HirExpr {
    @Override
    public final String toString() {
        return "_" + name;
    }
}
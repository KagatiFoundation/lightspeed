package org.kagati.ls.hir.node;

public final class HirConst extends HirExpr {
    private HirConstValue value;

    public HirConst(HirConstValue value) {
        this.value = value;
    }

    public HirConstValue value() {
        return value;
    }
}
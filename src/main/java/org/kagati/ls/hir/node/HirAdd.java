package org.kagati.ls.hir.node;

public final class HirAdd extends HirExpr {
    private HirExpr lhs, rhs;

    public HirAdd(HirExpr lhs, HirExpr rhs) {
        this.rhs = rhs;
        this.lhs = lhs;
    }

    public HirExpr lhs() {
        return lhs;
    }

    public HirExpr rhs() {
        return rhs;
    }
}
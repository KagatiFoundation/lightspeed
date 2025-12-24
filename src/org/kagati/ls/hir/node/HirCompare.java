package org.kagati.ls.hir.node;

public final class HirCompare implements HirExpr {
    public enum HirCompareType {
        EqEq
    }

    public final HirCompareType type;
    public final HirExpr lhs;
    public final HirExpr rhs;

    public HirCompare(HirExpr lhs, HirExpr rhs, HirCompareType type) {
        this.type = type;
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
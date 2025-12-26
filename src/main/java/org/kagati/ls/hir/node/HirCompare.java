package org.kagati.ls.hir.node;

public final class HirCompare extends HirExpr {
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

    public HirCompareType compareType() {
        return type;
    }

    public HirExpr lhs() {
        return lhs;
    }

    public HirExpr rhs() {
        return rhs;
    }
}
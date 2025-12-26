package org.kagati.ls.hir.node;

public final class HirAssign implements HirStmt {
    private String name;
    private HirExpr expr;

    public HirAssign(String name, HirExpr expr) {
        this.name = name;
        this.expr = expr;
    }

    public String name() {
        return name;
    }

    public HirExpr expr() {
        return expr;
    }
}
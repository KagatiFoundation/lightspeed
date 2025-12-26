package org.kagati.ls.hir.node;

public record HirAdd(HirExpr lhs, HirExpr rhs) implements HirExpr {
    
}
package org.kagati.ls.hir.node;

public sealed interface HirExpr extends HirNode 
    permits 
        HirConst, 
        HirCompare,
        HirAdd {
}
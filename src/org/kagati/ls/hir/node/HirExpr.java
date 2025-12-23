package org.kagati.ls.hir.node;

public sealed interface HirExpr extends HirNode permits HirParam, HirConst, HirVar {
    
}
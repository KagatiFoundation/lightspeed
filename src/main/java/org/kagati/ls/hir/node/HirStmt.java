package org.kagati.ls.hir.node;

public sealed interface HirStmt extends HirNode permits HirBlock, HirIf, HirAssign, HirFunction {
    
}
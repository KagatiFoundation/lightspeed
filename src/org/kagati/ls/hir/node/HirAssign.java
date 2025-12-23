package org.kagati.ls.hir.node;

public record HirAssign(String name, HirNode expr) implements HirStmt {
    
}
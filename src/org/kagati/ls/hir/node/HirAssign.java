package org.kagati.ls.hir.node;

public record HirAssign(String name, HirExpr expr) implements HirStmt { }
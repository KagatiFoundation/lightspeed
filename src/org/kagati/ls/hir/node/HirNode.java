package org.kagati.ls.hir.node;

public sealed interface HirNode permits HirExpr, HirStmt { }
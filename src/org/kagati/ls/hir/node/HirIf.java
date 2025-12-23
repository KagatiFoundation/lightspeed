package org.kagati.ls.hir.node;

public record HirIf(HirNode cond, HirBlock thenBlock, HirBlock elseBlock) implements HirStmt  { }
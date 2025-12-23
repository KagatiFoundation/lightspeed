package org.kagati.ls.hir.node;

public record If(IRNode cond, Block thenBlock, Block elseBlock) implements IRNode  { }
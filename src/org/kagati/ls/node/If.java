package org.kagati.ls.node;

public record If(IRNode cond, Block thenBlock, Block elseBlock) implements IRNode  { }
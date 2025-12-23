package org.kagati.ls.hir.node;

public sealed interface IRNode permits If, Block, Const, Var, Assign { }
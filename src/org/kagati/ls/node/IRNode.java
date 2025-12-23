package org.kagati.ls.node;

public sealed interface IRNode permits If, Block, Const, Var, Assign { }
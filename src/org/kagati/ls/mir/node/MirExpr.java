package org.kagati.ls.mir.node;

public sealed interface MirExpr permits MirConst, MirVar, MirAdd { }
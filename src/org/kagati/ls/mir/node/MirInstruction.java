package org.kagati.ls.mir.node;

public sealed interface MirInstruction permits MirAssign, MirAdd, MirConst {}
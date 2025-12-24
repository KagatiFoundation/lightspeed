package org.kagati.ls.mir.node;

public sealed interface MirInstruction permits MirConst, MirAssign {
    void dump();
}
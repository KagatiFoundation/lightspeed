package org.kagati.ls.mir.node;

import org.kagati.ls.mir.Temp;

public record MirAdd(Temp target, Temp lhs, Temp rhs) implements MirInstruction, MirExpr {
    @Override
    public String toString() {
        return String.format("%s = %s + %s", target.toString(), lhs.toString(), rhs.toString());
    }
}
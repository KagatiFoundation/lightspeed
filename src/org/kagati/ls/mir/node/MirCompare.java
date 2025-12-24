package org.kagati.ls.mir.node;

import org.kagati.ls.mir.Temp;

public record MirCompare(Temp target, Temp lhs, Temp rhs, MirCompareType type) implements MirInstruction {
    @Override
    public final String toString() {
        return String.format("%s == %s", lhs.toString(), rhs.toString());
    }
}
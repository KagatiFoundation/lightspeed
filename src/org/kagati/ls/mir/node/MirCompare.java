package org.kagati.ls.mir.node;

public record MirCompare(Temp target, Temp lhs, Temp rhs, MirCompareType type) implements MirInstruction {
    @Override
    public final String toString() {
        return String.format("%s = %s == %s", target.toString(), lhs.toString(), rhs.toString());
    }
}
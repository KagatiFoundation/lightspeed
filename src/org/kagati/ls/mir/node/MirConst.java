package org.kagati.ls.mir.node;

public record MirConst(Temp target, MirConstValue value) implements MirExpr, MirInstruction {
    @Override
    public String toString() {
        return switch (value) {
            case MirInteger i -> String.format("%s = const %d\n", target.toString(), i.value());
            case MirString s -> String.format("%s = const \"%s\"\n", target.toString(), s.value());
        };
    }    
}
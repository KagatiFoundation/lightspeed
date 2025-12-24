package org.kagati.ls.mir.node;

public record MirConst(String target, MirConstValue value) implements MirInstruction {
    @Override
    public void dump() {
        switch (value) {
            case MirInteger i -> System.out.printf("%s = %d\n", target, i.value());
            case MirString s -> System.out.printf("%s = %s\n", target, s.value());
        }
    }    
}
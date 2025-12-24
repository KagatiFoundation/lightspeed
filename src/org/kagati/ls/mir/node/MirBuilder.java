package org.kagati.ls.mir.node;

import java.util.ArrayList;
import java.util.List;

public final class MirBuilder {
    private final List<MirInstruction> instrs = new ArrayList<>();
    private int tempCounter = 0;

    public String nextTemp() {
        return "%t" + tempCounter++;
    }

    public void emit(MirInstruction instr) {
        instrs.add(instr);
    }

    public MirFunction build() {
        return new MirFunction(List.copyOf(instrs));
    } 
}
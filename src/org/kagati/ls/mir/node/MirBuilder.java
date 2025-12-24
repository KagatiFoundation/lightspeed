package org.kagati.ls.mir.node;

import java.util.ArrayList;
import java.util.List;

public final class MirBuilder {
    private int nextTemp = 0;
    private final List<MirInstruction> instrs = new ArrayList<>();

    public Temp freshTemp() {
        return new Temp(nextTemp++);
    }

    public void emit(MirInstruction i) {
        instrs.add(i);
    }

    public MirFunction build() {
        return new MirFunction(List.copyOf(instrs));
    }
}